package com.chiflepop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.ClienteProducto;

@Repository
public interface ClienteProductoRepository extends JpaRepository<ClienteProducto, Integer> {

}
