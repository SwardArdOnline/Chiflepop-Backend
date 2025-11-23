package com.chiflepop.demo.dto.admin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromocionDTO(
        String nombrePromocion,
        String descripcion,
        BigDecimal descuentoPorcentaje,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Boolean activo) {

}
