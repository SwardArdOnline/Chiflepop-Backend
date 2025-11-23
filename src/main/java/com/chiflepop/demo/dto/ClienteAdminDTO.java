package com.chiflepop.demo.dto;

import java.time.LocalDateTime;

public record ClienteAdminDTO(
    Integer clienteId,
    String nombre,
    String email,
    String telefono,
    String rol,
    LocalDateTime fechaRegistro
) {}