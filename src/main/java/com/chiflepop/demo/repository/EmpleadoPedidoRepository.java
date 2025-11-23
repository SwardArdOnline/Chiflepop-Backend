package com.chiflepop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.EmpleadoPedido;

@Repository
public interface EmpleadoPedidoRepository extends JpaRepository<EmpleadoPedido, Integer> {

}
