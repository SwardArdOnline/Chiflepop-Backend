package com.chiflepop.demo.services;

import com.chiflepop.demo.dto.CompraRequest;
import com.chiflepop.demo.dto.DetalleCompraDTO;
import com.chiflepop.demo.dto.DetallePedidoDTO;
import com.chiflepop.demo.dto.PedidoResponseDTO;
import com.chiflepop.demo.model.*;
import com.chiflepop.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private DireccionEntregaRepository direccionRepo;
    @Autowired
    private CuentaClienteRepository cuentaRepo;

    public PedidoResponseDTO obtenerPedidoPorId(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        return mapToDTO(pedido);
    }
    public List<PedidoResponseDTO> listarPedidosPorUsuario(Integer clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByCliente_ClienteIdOrderByFechaPedidoDesc(clienteId);
        return pedidos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    @Transactional
    public Pedido realizarCompra(Integer clienteId, CompraRequest request) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        DireccionEntrega direccion = direccionRepo.findById(request.direccionEntregaId())
                .filter(d -> d.getCliente().getClienteId().equals(clienteId))
                .orElseThrow(() -> new RuntimeException("Dirección inválida o no pertenece al cliente"));

        CuentaCliente cuenta = cuentaRepo.findById(request.cuentaClienteId())
                .filter(c -> c.getCliente().getClienteId().equals(clienteId))
                .orElseThrow(() -> new RuntimeException("Tarjeta inválida o no pertenece al cliente"));

        Estado estadoInicial = estadoRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Estado inicial no configurado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDireccionEntrega(direccion);
        pedido.setCuentaCliente(cuenta);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(estadoInicial);

        pedido = pedidoRepository.save(pedido);

        BigDecimal totalCompra = BigDecimal.ZERO;
        for (DetalleCompraDTO item : request.productos()) {
            Producto producto = productoRepository.findById(item.productoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < item.cantidad()) {
                throw new RuntimeException("Sin stock suficiente para: " + producto.getNombre());
            }

            BigDecimal precio = producto.getPrecio();
            BigDecimal subtotal = precio.multiply(new BigDecimal(item.cantidad()));
            totalCompra = totalCompra.add(subtotal);

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.cantidad());
            detalle.setPrecioUnitario(precio);

            detallePedidoRepository.save(detalle);
            producto.setStock(producto.getStock() - item.cantidad());
            productoRepository.save(producto);
        }

        if (cuenta.getSaldo().compareTo(totalCompra) < 0) {
            throw new RuntimeException("Saldo insuficiente en la tarjeta seleccionada");
        }

        cuenta.setSaldo(cuenta.getSaldo().subtract(totalCompra));
        cuentaRepo.save(cuenta);

        pedido.setTotal(totalCompra);
        return pedidoRepository.save(pedido);
    }
    private PedidoResponseDTO mapToDTO(Pedido p) {
        List<DetallePedidoDTO> detallesDTO = p.getDetalles().stream().map(d -> new DetallePedidoDTO(
            d.getProducto().getNombre(),
            "https://via.placeholder.com/150", 
            d.getCantidad(),
            d.getPrecioUnitario(),
            d.getPrecioUnitario().multiply(new BigDecimal(d.getCantidad()))
        )).collect(Collectors.toList());

        return new PedidoResponseDTO(
            p.getPedidoId(),
            p.getFechaPedido(),
            p.getEstado().getDescripcion(),
            p.getTotal(),
            p.getDireccionEntrega().getDireccion() + ", " + p.getDireccionEntrega().getCiudad(),
            p.getCuentaCliente().getBanco().getNombreBanco() + " (" + p.getCuentaCliente().getMetodoPago().getNombreMetodo() + ")",
            detallesDTO
        );
    }
}
