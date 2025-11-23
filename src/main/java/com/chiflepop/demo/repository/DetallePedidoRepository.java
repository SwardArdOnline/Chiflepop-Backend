package com.chiflepop.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    List<DetallePedido> findTopByPedido_Cliente_ClienteIdOrderByPedido_FechaPedidoDesc(Integer clienteId);
}
