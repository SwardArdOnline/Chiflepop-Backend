package com.chiflepop.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.CuentaCliente;

@Repository
public interface CuentaClienteRepository extends JpaRepository<CuentaCliente, Integer> {
    List<CuentaCliente> findByCliente_ClienteId(Integer clienteId);
}
