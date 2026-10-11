package com.chiflepop.demo.controllers;

import com.chiflepop.demo.dto.CompraRequest;
import com.chiflepop.demo.dto.DetalleCompraDTO;
import com.chiflepop.demo.dto.PedidoResponseDTO;
import com.chiflepop.demo.model.Pedido;
import com.chiflepop.demo.services.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
@AutoConfigureMockMvc(addFilters = false) // desactiva Spring Security para probar solo el controller
class PedidoControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private PedidoService pedidoService;

    @Test
    @DisplayName("POST /api/pedidos/comprar: compra válida responde 200 con el id del pedido")
    void comprar_ok() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setPedidoId(55);
        when(pedidoService.realizarCompra(eq(1), any(CompraRequest.class))).thenReturn(pedido);

        CompraRequest body = new CompraRequest(10, 20, List.of(new DetalleCompraDTO(100, 2)));

        mockMvc.perform(post("/api/pedidos/comprar")
                        .param("clienteId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidoId").value(55))
                .andExpect(jsonPath("$.mensaje").value("Pedido creado con éxito"));
    }

    @Test
    @DisplayName("POST /api/pedidos/comprar: error de negocio responde 400 con el mensaje")
    void comprar_errorDeNegocio() throws Exception {
        when(pedidoService.realizarCompra(eq(1), any(CompraRequest.class)))
                .thenThrow(new RuntimeException("Saldo insuficiente en la tarjeta seleccionada"));

        CompraRequest body = new CompraRequest(10, 20, List.of(new DetalleCompraDTO(100, 2)));

        mockMvc.perform(post("/api/pedidos/comprar")
                        .param("clienteId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Saldo insuficiente en la tarjeta seleccionada"));
    }

    @Test
    @DisplayName("POST /api/pedidos/comprar: lista de productos vacía responde 400 (validación @Valid)")
    void comprar_validacionFalla() throws Exception {
        CompraRequest body = new CompraRequest(10, 20, List.of());

        mockMvc.perform(post("/api/pedidos/comprar")
                        .param("clienteId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(pedidoService);
    }

    @Test
    @DisplayName("GET /api/pedidos/{id}: devuelve el pedido en formato DTO")
    void obtenerPedido_ok() throws Exception {
        PedidoResponseDTO dto = new PedidoResponseDTO(
                5, LocalDateTime.of(2025, 12, 1, 10, 0), "Pendiente",
                new BigDecimal("20.00"), "Av. Larco 123, Lima", "BCP (Tarjeta)", List.of());
        when(pedidoService.obtenerPedidoPorId(5)).thenReturn(dto);

        mockMvc.perform(get("/api/pedidos/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.estado").value("Pendiente"))
                .andExpect(jsonPath("$.metodoPago").value("BCP (Tarjeta)"));
    }
}
