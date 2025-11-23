package com.chiflepop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.TipoBanco;

@Repository
public interface TipoBancoRepository extends JpaRepository<TipoBanco, Integer> {

}