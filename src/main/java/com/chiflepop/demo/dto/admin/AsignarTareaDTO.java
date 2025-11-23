package com.chiflepop.demo.dto.admin;

public record AsignarTareaDTO(
        Integer empleadoId,
        Integer pedidoId,
        String funcion
    ) {}