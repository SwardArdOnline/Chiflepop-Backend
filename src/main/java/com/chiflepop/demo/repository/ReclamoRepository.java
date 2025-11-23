package com.chiflepop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chiflepop.demo.model.Reclamo;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Integer> {

}
