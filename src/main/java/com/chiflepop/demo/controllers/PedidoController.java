package com.chiflepop.demo.controllers;

import com.chiflepop.demo.dto.CompraRequest;
import com.chiflepop.demo.model.Pedido;
import com.chiflepop.demo.services.PedidoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    // POST: /api/pedidos/comprar?clienteId=1
    @PostMapping("/comprar")
    public ResponseEntity<?> crearPedido(
            @RequestParam Integer clienteId,
            @Valid @RequestBody CompraRequest compraRequest) {
        try {
            Pedido nuevoPedido = pedidoService.realizarCompra(clienteId, compraRequest);
            return ResponseEntity.ok("Pedido creado con éxito. ID: " + nuevoPedido.getPedidoId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
