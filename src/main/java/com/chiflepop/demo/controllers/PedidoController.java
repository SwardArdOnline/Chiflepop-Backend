package com.chiflepop.demo.controllers;

import com.chiflepop.demo.dto.CompraRequest;
import com.chiflepop.demo.dto.PedidoResponseDTO;
import com.chiflepop.demo.model.Pedido;
import com.chiflepop.demo.services.PedidoService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/comprar")
    public ResponseEntity<?> crearPedido(
            @RequestParam Integer clienteId,
            @Valid @RequestBody CompraRequest compraRequest) {
        try {
            Pedido nuevoPedido = pedidoService.realizarCompra(clienteId, compraRequest);
            
            return ResponseEntity.ok(Map.of(
                "mensaje", "Pedido creado con éxito",
                "pedidoId", nuevoPedido.getPedidoId()
            ));
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> getPedidoById(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.obtenerPedidoPorId(id));
    }

    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> getMisPedidos(@RequestParam Integer clienteId) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorUsuario(clienteId));
    }
}
