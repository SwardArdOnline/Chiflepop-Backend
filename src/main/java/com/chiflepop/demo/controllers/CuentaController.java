package com.chiflepop.demo.controllers;

import com.chiflepop.demo.dto.CuentaDTO;
import com.chiflepop.demo.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> obtenerCuentas(@RequestParam Integer clienteId) {
        return ResponseEntity.ok(cuentaService.listarCuentasPorUsuario(clienteId));
    }
}