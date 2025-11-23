package com.chiflepop.demo.dto.admin;

import java.time.LocalDate;

public record EmpleadoDTO(
        String nombre,
        String cargo,
        LocalDate fechaIngreso
    ) {}