package com.chiflepop.demo.services;

import com.chiflepop.demo.dto.DashboardStatsDTO;
import com.chiflepop.demo.model.Cliente;
import com.chiflepop.demo.model.DetallePedido;
import com.chiflepop.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private DetallePedidoRepository detallePedidoRepository;

    public DashboardStatsDTO obtenerEstadisticas(Integer clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        long cantidadPedidos = pedidoRepository.countByCliente_ClienteId(clienteId);

        BigDecimal totalGastado = pedidoRepository.sumTotalGastado(clienteId);
        if (totalGastado == null) totalGastado = BigDecimal.ZERO;

        long totalProductos = productoRepository.count();

        String ultimoProducto = "Ninguno aún";
        List<DetallePedido> recientes = detallePedidoRepository.findTopByPedido_Cliente_ClienteIdOrderByPedido_FechaPedidoDesc(clienteId);
        if (!recientes.isEmpty()) {
            ultimoProducto = recientes.get(0).getProducto().getNombre();
        }
        return new DashboardStatsDTO(
            cliente.getNombre(),
            cantidadPedidos,
            totalGastado,
            totalProductos,
            ultimoProducto
        );
    }
}