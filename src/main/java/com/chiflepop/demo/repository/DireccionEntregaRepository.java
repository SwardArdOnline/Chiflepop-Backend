package com.chiflepop.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.DireccionEntrega;

@Repository
public interface DireccionEntregaRepository extends JpaRepository<DireccionEntrega, Integer> {
    List<DireccionEntrega> findByCliente_ClienteId(Integer clienteId);
}
