package com.chiflepop.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DetalleCompraDTO(
    @NotNull(message = "El ID del producto es obligatorio")
    Integer productoId,

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    Integer cantidad
) {

}
