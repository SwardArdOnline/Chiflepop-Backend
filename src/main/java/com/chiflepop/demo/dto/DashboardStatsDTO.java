package com.chiflepop.demo.dto;

import java.math.BigDecimal;

public record DashboardStatsDTO(
    String nombreUsuario,
    Long pedidosRealizados,
    BigDecimal totalGastado,
    Long totalProductosDisponibles,
    String ultimoProductoComprado
) {}