package com.chiflepop.demo.controllers;

import com.chiflepop.demo.dto.admin.ProductoDTO;
import com.chiflepop.demo.dto.admin.PromocionDTO;
import com.chiflepop.demo.model.*;
import com.chiflepop.demo.services.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(adminService.listarClientes());
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        adminService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // --- GESTIÓN DE PRODUCTOS ---

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(adminService.listarProductos());
    }

    @PostMapping("/productos")
    public ResponseEntity<Producto> crearProducto(@RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(adminService.guardarProducto(dto));
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> editarProducto(@PathVariable Integer id, @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(adminService.actualizarProducto(id, dto));
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        adminService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/promociones")
    public ResponseEntity<List<Promocion>> listarPromociones() {
        return ResponseEntity.ok(adminService.listarPromociones());
    }

    @PostMapping("/promociones")
    public ResponseEntity<Promocion> crearPromocion(@RequestBody PromocionDTO dto) {
        return ResponseEntity.ok(adminService.guardarPromocion(dto));
    }

    @PutMapping("/promociones/{id}")
    public ResponseEntity<Promocion> editarPromocion(@PathVariable Integer id, @RequestBody PromocionDTO dto) {
        return ResponseEntity.ok(adminService.actualizarPromocion(id, dto));
    }

    @DeleteMapping("/promociones/{id}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable Integer id) {
        adminService.eliminarPromocion(id);
        return ResponseEntity.noContent().build();
    }
}