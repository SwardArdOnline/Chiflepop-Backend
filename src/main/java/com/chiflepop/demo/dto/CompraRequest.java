package com.chiflepop.demo.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CompraRequest(
    @NotNull(message = "Debes seleccionar una dirección de entrega")
    Integer direccionEntregaId,

    @NotNull(message = "Debes seleccionar una tarjeta/cuenta para el pago")
    Integer cuentaClienteId,

    @NotEmpty(message = "La lista de productos no puede estar vacía")
    List<DetalleCompraDTO> productos
) {

}
