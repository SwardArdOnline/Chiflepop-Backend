package com.chiflepop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.DireccionEntrega;

@Repository
public interface DireccionEntregaRepository extends JpaRepository<DireccionEntrega, Integer> {

}
