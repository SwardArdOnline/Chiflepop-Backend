package com.chiflepop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Integer> {

}
