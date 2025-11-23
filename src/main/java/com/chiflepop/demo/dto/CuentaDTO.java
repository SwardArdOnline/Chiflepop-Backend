package com.chiflepop.demo.dto;

import java.math.BigDecimal;

public record CuentaDTO(
    Integer id,
    String banco,
    String numeroCuenta,
    String tipo,
    BigDecimal saldo,
    Boolean esPrincipal
) {}