package com.chiflepop.demo.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    long countByCliente_ClienteId(Integer clienteId);

    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.cliente.clienteId = :clienteId")
    BigDecimal sumTotalGastado(Integer clienteId);
    List<Pedido> findByCliente_ClienteIdOrderByFechaPedidoDesc(Integer clienteId);

}
