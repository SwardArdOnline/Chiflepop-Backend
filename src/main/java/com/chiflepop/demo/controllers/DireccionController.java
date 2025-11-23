package com.chiflepop.demo.controllers;

import com.chiflepop.demo.model.DireccionEntrega;
import com.chiflepop.demo.repository.DireccionEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    @Autowired
    private DireccionEntregaRepository direccionRepository;

    @GetMapping
    public ResponseEntity<List<DireccionEntrega>> misDirecciones(@RequestParam Integer clienteId) {
        return ResponseEntity.ok(direccionRepository.findByCliente_ClienteId(clienteId));
    }
}