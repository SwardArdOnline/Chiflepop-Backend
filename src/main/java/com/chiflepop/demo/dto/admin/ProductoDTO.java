package com.chiflepop.demo.dto.admin;

import java.math.BigDecimal;

public record ProductoDTO(
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer stock) {

}
