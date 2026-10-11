package com.chiflepop.demo.controllers;

import com.chiflepop.demo.model.Cliente;
import com.chiflepop.demo.services.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private AuthService authService;

    private static final String CREDENCIALES = "{\"email\":\"ana@correo.com\",\"password\":\"1234\"}";

    @Test
    @DisplayName("POST /auth/login: credenciales válidas responden 200 con rol y clienteId")
    void login_ok() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setClienteId(3);
        cliente.setRol("ROLE_CLIENTE");
        when(authService.login("ana@correo.com", "1234")).thenReturn(cliente);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREDENCIALES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Login exitoso"))
                .andExpect(jsonPath("$.rol").value("ROLE_CLIENTE"))
                .andExpect(jsonPath("$.clienteId").value(3));
    }

    @Test
    @DisplayName("POST /auth/login: credenciales inválidas responden 401")
    void login_credencialesIncorrectas() throws Exception {
        when(authService.login("ana@correo.com", "1234")).thenReturn(null);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREDENCIALES))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciales incorrectas"));
    }
}
