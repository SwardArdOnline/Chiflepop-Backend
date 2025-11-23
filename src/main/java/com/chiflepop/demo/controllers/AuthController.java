package com.chiflepop.demo.controllers;

import com.chiflepop.demo.model.Cliente;
import com.chiflepop.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(authService.registrar(cliente));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        Cliente usuario = authService.login(email, password);

        if (usuario != null) {
            return ResponseEntity.ok(Map.of(
                "mensaje", "Login exitoso",
                "rol", usuario.getRol(),
                "clienteId", usuario.getClienteId()
            ));
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
}