package com.chiflepop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Integer> {

}
