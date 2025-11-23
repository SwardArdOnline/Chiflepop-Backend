package com.chiflepop.demo.dto;

import java.math.BigDecimal;

public record CrearCuentaDTO(
    Integer bancoId,
    Integer metodoPagoId,
    String numeroCuenta,
    BigDecimal saldoInicial
) {}