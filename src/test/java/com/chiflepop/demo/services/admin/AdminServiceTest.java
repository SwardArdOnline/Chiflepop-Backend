package com.chiflepop.demo.services.admin;

import com.chiflepop.demo.dto.admin.AsignarTareaDTO;
import com.chiflepop.demo.dto.admin.ProductoDTO;
import com.chiflepop.demo.model.Empleado;
import com.chiflepop.demo.model.EmpleadoPedido;
import com.chiflepop.demo.model.Pedido;
import com.chiflepop.demo.model.Producto;
import com.chiflepop.demo.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock private ClienteRepository clienteRepository;
    @Mock private ProductoRepository productoRepository;
    @Mock private PromocionRepository promocionRepository;
    @Mock private EmpleadoRepository empleadoRepository;
    @Mock private EmpleadoPedidoRepository empleadoPedidoRepository;
    @Mock private PedidoRepository pedidoRepository;

    @InjectMocks private AdminService adminService;

    private final ProductoDTO productoDto =
            new ProductoDTO("Chiflepop Picante", "Chifle con ají", new BigDecimal("12.50"), 40, "img.png");

    @Test
    @DisplayName("eliminarCliente: si no existe lanza excepción y no borra nada")
    void eliminarCliente_noExiste() {
        when(clienteRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> adminService.eliminarCliente(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cliente no encontrado");
        verify(clienteRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("eliminarCliente: si existe, lo elimina")
    void eliminarCliente_existe() {
        when(clienteRepository.existsById(1)).thenReturn(true);

        adminService.eliminarCliente(1);

        verify(clienteRepository).deleteById(1);
    }

    @Test
    @DisplayName("guardarProducto: copia todos los campos del DTO")
    void guardarProducto_mapeaCampos() {
        when(productoRepository.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        adminService.guardarProducto(productoDto);

        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).save(captor.capture());
        Producto guardado = captor.getValue();
        assertThat(guardado.getNombre()).isEqualTo("Chiflepop Picante");
        assertThat(guardado.getDescripcion()).isEqualTo("Chifle con ají");
        assertThat(guardado.getPrecio()).isEqualByComparingTo("12.50");
        assertThat(guardado.getStock()).isEqualTo(40);
        assertThat(guardado.getImagen()).isEqualTo("img.png");
    }

    @Test
    @DisplayName("actualizarProducto: modifica el producto existente")
    void actualizarProducto_existe() {
        Producto existente = new Producto();
        existente.setProductoId(1);
        existente.setNombre("Viejo");
        when(productoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(productoRepository.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        Producto resultado = adminService.actualizarProducto(1, productoDto);

        assertThat(resultado.getProductoId()).isEqualTo(1);
        assertThat(resultado.getNombre()).isEqualTo("Chiflepop Picante");
    }

    @Test
    @DisplayName("actualizarProducto: producto inexistente lanza excepción")
    void actualizarProducto_noExiste() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.actualizarProducto(99, productoDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Producto no encontrado");
    }

    @Test
    @DisplayName("eliminarProducto: si no existe lanza excepción")
    void eliminarProducto_noExiste() {
        when(productoRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> adminService.eliminarProducto(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Producto no encontrado");
    }

    @Test
    @DisplayName("asignarEmpleadoAPedido: crea la asignación con su función")
    void asignarEmpleadoAPedido_ok() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(new Empleado()));
        when(pedidoRepository.findById(2)).thenReturn(Optional.of(new Pedido()));
        when(empleadoPedidoRepository.save(any(EmpleadoPedido.class))).thenAnswer(inv -> inv.getArgument(0));

        EmpleadoPedido resultado = adminService.asignarEmpleadoAPedido(new AsignarTareaDTO(1, 2, "Empaquetado"));

        assertThat(resultado.getRolEnPedido()).isEqualTo("Empaquetado");
        assertThat(resultado.getEmpleado()).isNotNull();
        assertThat(resultado.getPedido()).isNotNull();
    }

    @Test
    @DisplayName("asignarEmpleadoAPedido: empleado inexistente lanza excepción")
    void asignarEmpleadoAPedido_empleadoNoExiste() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.asignarEmpleadoAPedido(new AsignarTareaDTO(1, 2, "Empaquetado")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Empleado no existe");
    }

    @Test
    @DisplayName("asignarEmpleadoAPedido: pedido inexistente lanza excepción")
    void asignarEmpleadoAPedido_pedidoNoExiste() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(new Empleado()));
        when(pedidoRepository.findById(2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.asignarEmpleadoAPedido(new AsignarTareaDTO(1, 2, "Empaquetado")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Pedido no existe");
    }
}
