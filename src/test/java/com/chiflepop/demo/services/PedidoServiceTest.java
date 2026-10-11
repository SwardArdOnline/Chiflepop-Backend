package com.chiflepop.demo.services;

import com.chiflepop.demo.dto.CompraRequest;
import com.chiflepop.demo.dto.DetalleCompraDTO;
import com.chiflepop.demo.dto.PedidoResponseDTO;
import com.chiflepop.demo.model.*;
import com.chiflepop.demo.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock private PedidoRepository pedidoRepository;
    @Mock private DetallePedidoRepository detallePedidoRepository;
    @Mock private ProductoRepository productoRepository;
    @Mock private ClienteRepository clienteRepository;
    @Mock private EstadoRepository estadoRepository;
    @Mock private DireccionEntregaRepository direccionRepo;
    @Mock private CuentaClienteRepository cuentaRepo;

    @InjectMocks private PedidoService pedidoService;

    // ---------- helpers para armar datos de prueba ----------

    private Cliente cliente(int id) {
        Cliente c = new Cliente();
        c.setClienteId(id);
        c.setNombre("Ana");
        return c;
    }

    private DireccionEntrega direccion(Cliente dueno) {
        DireccionEntrega d = new DireccionEntrega();
        d.setDireccionEntregaId(10);
        d.setCliente(dueno);
        d.setDireccion("Av. Larco 123");
        d.setCiudad("Lima");
        return d;
    }

    private CuentaCliente cuenta(Cliente dueno, String saldo) {
        CuentaCliente c = new CuentaCliente();
        c.setCuentaClienteId(20);
        c.setCliente(dueno);
        c.setSaldo(new BigDecimal(saldo));
        return c;
    }

    private Estado estado(String descripcion) {
        Estado e = new Estado();
        e.setEstadoId(1);
        e.setDescripcion(descripcion);
        return e;
    }

    private Producto producto(int id, String nombre, String precio, int stock) {
        Producto p = new Producto();
        p.setProductoId(id);
        p.setNombre(nombre);
        p.setPrecio(new BigDecimal(precio));
        p.setStock(stock);
        return p;
    }

    private CompraRequest compra(int productoId, int cantidad) {
        return new CompraRequest(10, 20, List.of(new DetalleCompraDTO(productoId, cantidad)));
    }

    /** Prepara los mocks del "camino feliz" previo al ciclo de productos. */
    private void stubDatosBasicos(Cliente cliente, CuentaCliente cuenta) {
        lenient().when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        lenient().when(direccionRepo.findById(10)).thenReturn(Optional.of(direccion(cliente)));
        lenient().when(cuentaRepo.findById(20)).thenReturn(Optional.of(cuenta));
        lenient().when(estadoRepository.findById(1)).thenReturn(Optional.of(estado("Pendiente")));
        lenient().when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    // ---------- realizarCompra ----------

    @Test
    @DisplayName("realizarCompra: calcula el total, descuenta stock y descuenta saldo")
    void realizarCompra_exitosa() {
        Cliente cliente = cliente(1);
        CuentaCliente cuenta = cuenta(cliente, "100.00");
        Producto producto = producto(100, "Chiflepop Picante", "10.00", 5);
        stubDatosBasicos(cliente, cuenta);
        when(productoRepository.findById(100)).thenReturn(Optional.of(producto));

        Pedido pedido = pedidoService.realizarCompra(1, compra(100, 2));

        assertThat(pedido.getTotal()).isEqualByComparingTo("20.00");
        assertThat(cuenta.getSaldo()).isEqualByComparingTo("80.00");
        assertThat(producto.getStock()).isEqualTo(3);
        verify(detallePedidoRepository).save(any(DetallePedido.class));
        verify(cuentaRepo).save(cuenta);
    }

    @Test
    @DisplayName("realizarCompra: cliente inexistente lanza excepción")
    void realizarCompra_clienteNoExiste() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.realizarCompra(1, compra(100, 1)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cliente no encontrado");
    }

    @Test
    @DisplayName("realizarCompra: dirección de otro cliente es rechazada")
    void realizarCompra_direccionDeOtroCliente() {
        Cliente cliente = cliente(1);
        Cliente otro = cliente(2);
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(direccionRepo.findById(10)).thenReturn(Optional.of(direccion(otro)));

        assertThatThrownBy(() -> pedidoService.realizarCompra(1, compra(100, 1)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Dirección inválida o no pertenece al cliente");
    }

    @Test
    @DisplayName("realizarCompra: tarjeta de otro cliente es rechazada")
    void realizarCompra_tarjetaDeOtroCliente() {
        Cliente cliente = cliente(1);
        Cliente otro = cliente(2);
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(direccionRepo.findById(10)).thenReturn(Optional.of(direccion(cliente)));
        when(cuentaRepo.findById(20)).thenReturn(Optional.of(cuenta(otro, "100.00")));

        assertThatThrownBy(() -> pedidoService.realizarCompra(1, compra(100, 1)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Tarjeta inválida o no pertenece al cliente");
    }

    @Test
    @DisplayName("realizarCompra: sin estado inicial configurado lanza excepción")
    void realizarCompra_estadoInicialNoConfigurado() {
        Cliente cliente = cliente(1);
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(direccionRepo.findById(10)).thenReturn(Optional.of(direccion(cliente)));
        when(cuentaRepo.findById(20)).thenReturn(Optional.of(cuenta(cliente, "100.00")));
        when(estadoRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.realizarCompra(1, compra(100, 1)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Estado inicial no configurado");
    }

    @Test
    @DisplayName("realizarCompra: producto inexistente lanza excepción")
    void realizarCompra_productoNoExiste() {
        Cliente cliente = cliente(1);
        stubDatosBasicos(cliente, cuenta(cliente, "100.00"));
        when(productoRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.realizarCompra(1, compra(999, 1)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Producto no encontrado");
    }

    @Test
    @DisplayName("realizarCompra: stock insuficiente indica el nombre del producto")
    void realizarCompra_stockInsuficiente() {
        Cliente cliente = cliente(1);
        stubDatosBasicos(cliente, cuenta(cliente, "100.00"));
        when(productoRepository.findById(100))
                .thenReturn(Optional.of(producto(100, "Chiflepop Picante", "10.00", 1)));

        assertThatThrownBy(() -> pedidoService.realizarCompra(1, compra(100, 2)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Sin stock suficiente para: Chiflepop Picante");
    }

    @Test
    @DisplayName("realizarCompra: saldo insuficiente lanza excepción y no descuenta saldo")
    void realizarCompra_saldoInsuficiente() {
        Cliente cliente = cliente(1);
        CuentaCliente cuenta = cuenta(cliente, "5.00");
        stubDatosBasicos(cliente, cuenta);
        when(productoRepository.findById(100))
                .thenReturn(Optional.of(producto(100, "Chiflepop Picante", "10.00", 5)));

        assertThatThrownBy(() -> pedidoService.realizarCompra(1, compra(100, 2)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Saldo insuficiente en la tarjeta seleccionada");

        assertThat(cuenta.getSaldo()).isEqualByComparingTo("5.00");
        verify(cuentaRepo, never()).save(any(CuentaCliente.class));
    }

    // ---------- consultas ----------

    @Test
    @DisplayName("obtenerPedidoPorId: pedido inexistente lanza excepción")
    void obtenerPedidoPorId_noExiste() {
        when(pedidoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.obtenerPedidoPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Pedido no encontrado");
    }

    @Test
    @DisplayName("obtenerPedidoPorId: mapea correctamente el pedido a DTO")
    void obtenerPedidoPorId_mapeaDTO() {
        Cliente cliente = cliente(1);

        Banco banco = new Banco();
        banco.setNombreBanco("BCP");
        MetodoPago metodo = new MetodoPago();
        metodo.setNombreMetodo("Tarjeta");
        CuentaCliente cuenta = cuenta(cliente, "100.00");
        cuenta.setBanco(banco);
        cuenta.setMetodoPago(metodo);

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto(100, "Chiflepop Picante", "10.00", 5));
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(new BigDecimal("10.00"));

        Pedido pedido = new Pedido();
        pedido.setPedidoId(5);
        pedido.setFechaPedido(LocalDateTime.of(2025, 12, 1, 10, 0));
        pedido.setEstado(estado("Pendiente"));
        pedido.setTotal(new BigDecimal("20.00"));
        pedido.setDireccionEntrega(direccion(cliente));
        pedido.setCuentaCliente(cuenta);
        pedido.setDetalles(Set.of(detalle));

        when(pedidoRepository.findById(5)).thenReturn(Optional.of(pedido));

        PedidoResponseDTO dto = pedidoService.obtenerPedidoPorId(5);

        assertThat(dto.id()).isEqualTo(5);
        assertThat(dto.estado()).isEqualTo("Pendiente");
        assertThat(dto.direccionEntrega()).isEqualTo("Av. Larco 123, Lima");
        assertThat(dto.metodoPago()).isEqualTo("BCP (Tarjeta)");
        assertThat(dto.detalles()).hasSize(1);
        assertThat(dto.detalles().get(0).producto()).isEqualTo("Chiflepop Picante");
        assertThat(dto.detalles().get(0).subtotal()).isEqualByComparingTo("20.00");
    }

    @Test
    @DisplayName("listarPedidosPorUsuario: sin pedidos devuelve lista vacía")
    void listarPedidosPorUsuario_vacio() {
        when(pedidoRepository.findByCliente_ClienteIdOrderByFechaPedidoDesc(1)).thenReturn(List.of());

        assertThat(pedidoService.listarPedidosPorUsuario(1)).isEmpty();
    }
}
