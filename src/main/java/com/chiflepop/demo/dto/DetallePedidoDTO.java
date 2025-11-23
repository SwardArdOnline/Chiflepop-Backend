package com.chiflepop.demo.dto;

import java.math.BigDecimal;

public record DetallePedidoDTO(
    String producto,
    String imagen,
    Integer cantidad,
    BigDecimal precioUnitario,
    BigDecimal subtotal
) {}