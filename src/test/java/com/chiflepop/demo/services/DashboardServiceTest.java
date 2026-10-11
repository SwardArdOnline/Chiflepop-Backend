package com.chiflepop.demo.services;

import com.chiflepop.demo.dto.DashboardStatsDTO;
import com.chiflepop.demo.model.Cliente;
import com.chiflepop.demo.model.DetallePedido;
import com.chiflepop.demo.model.Producto;
import com.chiflepop.demo.repository.ClienteRepository;
import com.chiflepop.demo.repository.DetallePedidoRepository;
import com.chiflepop.demo.repository.PedidoRepository;
import com.chiflepop.demo.repository.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock private ClienteRepository clienteRepository;
    @Mock private PedidoRepository pedidoRepository;
    @Mock private ProductoRepository productoRepository;
    @Mock private DetallePedidoRepository detallePedidoRepository;

    @InjectMocks private DashboardService dashboardService;

    private Cliente cliente() {
        Cliente c = new Cliente();
        c.setClienteId(1);
        c.setNombre("Ana");
        return c;
    }

    @Test
    @DisplayName("obtenerEstadisticas: cliente sin pedidos -> total 0 y 'Ninguno aún'")
    void estadisticas_sinPedidos() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente()));
        when(pedidoRepository.countByCliente_ClienteId(1)).thenReturn(0L);
        when(pedidoRepository.sumTotalGastado(1)).thenReturn(null); // SUM() sin filas devuelve null
        when(productoRepository.count()).thenReturn(7L);
        when(detallePedidoRepository.findTopByPedido_Cliente_ClienteIdOrderByPedido_FechaPedidoDesc(1))
                .thenReturn(List.of());

        DashboardStatsDTO stats = dashboardService.obtenerEstadisticas(1);

        assertThat(stats.nombreUsuario()).isEqualTo("Ana");
        assertThat(stats.pedidosRealizados()).isZero();
        assertThat(stats.totalGastado()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(stats.totalProductosDisponibles()).isEqualTo(7L);
        assertThat(stats.ultimoProductoComprado()).isEqualTo("Ninguno aún");
    }

    @Test
    @DisplayName("obtenerEstadisticas: cliente con pedidos devuelve sus datos reales")
    void estadisticas_conPedidos() {
        Producto producto = new Producto();
        producto.setNombre("Chiflepop Picante");
        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente()));
        when(pedidoRepository.countByCliente_ClienteId(1)).thenReturn(3L);
        when(pedidoRepository.sumTotalGastado(1)).thenReturn(new BigDecimal("150.50"));
        when(productoRepository.count()).thenReturn(7L);
        when(detallePedidoRepository.findTopByPedido_Cliente_ClienteIdOrderByPedido_FechaPedidoDesc(1))
                .thenReturn(List.of(detalle));

        DashboardStatsDTO stats = dashboardService.obtenerEstadisticas(1);

        assertThat(stats.pedidosRealizados()).isEqualTo(3L);
        assertThat(stats.totalGastado()).isEqualByComparingTo("150.50");
        assertThat(stats.ultimoProductoComprado()).isEqualTo("Chiflepop Picante");
    }

    @Test
    @DisplayName("obtenerEstadisticas: cliente inexistente lanza excepción")
    void estadisticas_clienteNoExiste() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dashboardService.obtenerEstadisticas(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cliente no encontrado");
    }
}
