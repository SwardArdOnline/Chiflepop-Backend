package com.chiflepop.demo.services;

import com.chiflepop.demo.dto.CuentaDTO;
import com.chiflepop.demo.model.CuentaCliente;
import com.chiflepop.demo.repository.CuentaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private CuentaClienteRepository cuentaRepository;

    public List<CuentaDTO> listarCuentasPorUsuario(Integer clienteId) {
        List<CuentaCliente> cuentas = cuentaRepository.findByCliente_ClienteId(clienteId);
        
        return cuentas.stream().map(c -> new CuentaDTO(
            c.getCuentaClienteId(),
            c.getBanco().getNombreBanco(),
            c.getNumeroCuenta(),
            c.getMetodoPago().getNombreMetodo(),
            c.getSaldo(),
            c.getEsPrincipal()
        )).collect(Collectors.toList());
    }
}