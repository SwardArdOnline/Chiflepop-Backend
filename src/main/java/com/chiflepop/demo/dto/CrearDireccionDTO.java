package com.chiflepop.demo.dto;

public record CrearDireccionDTO(
    String direccion,
    String ciudad,
    String departamento,
    String pais,
    String referencia
) {}