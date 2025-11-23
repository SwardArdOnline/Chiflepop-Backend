package com.chiflepop.demo.services;

import com.chiflepop.demo.dto.CrearCuentaDTO;
import com.chiflepop.demo.dto.CuentaDTO;
import com.chiflepop.demo.model.Banco;
import com.chiflepop.demo.model.Cliente;
import com.chiflepop.demo.model.CuentaCliente;
import com.chiflepop.demo.model.MetodoPago;
import com.chiflepop.demo.repository.BancoRepository;
import com.chiflepop.demo.repository.ClienteRepository;
import com.chiflepop.demo.repository.CuentaClienteRepository;
import com.chiflepop.demo.repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private CuentaClienteRepository cuentaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private BancoRepository bancoRepository;
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<CuentaDTO> listarCuentasPorUsuario(Integer clienteId) {
        List<CuentaCliente> cuentas = cuentaRepository.findByCliente_ClienteId(clienteId);

        return cuentas.stream().map(c -> new CuentaDTO(
                c.getCuentaClienteId(),
                c.getBanco().getNombreBanco(),
                c.getNumeroCuenta(),
                c.getMetodoPago().getNombreMetodo(),
                c.getSaldo(),
                c.getEsPrincipal())).collect(Collectors.toList());
    }

    @Transactional
    public CuentaDTO crearCuenta(Integer clienteId, CrearCuentaDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Banco banco = bancoRepository.findById(dto.bancoId())
                .orElseThrow(() -> new RuntimeException("Banco no encontrado"));

        MetodoPago metodoPago = metodoPagoRepository.findById(dto.metodoPagoId())
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        CuentaCliente cuenta = new CuentaCliente();
        cuenta.setCliente(cliente);
        cuenta.setBanco(banco);
        cuenta.setMetodoPago(metodoPago);
        cuenta.setNumeroCuenta(dto.numeroCuenta());
        cuenta.setSaldo(dto.saldoInicial());
        cuenta.setFechaRegistro(LocalDateTime.now());

        boolean tieneCuentas = cuentaRepository.findByCliente_ClienteId(clienteId).size() > 0;
        cuenta.setEsPrincipal(!tieneCuentas);

        CuentaCliente guardada = cuentaRepository.save(cuenta);

        return new CuentaDTO(
                guardada.getCuentaClienteId(),
                guardada.getBanco().getNombreBanco(),
                guardada.getNumeroCuenta(),
                guardada.getMetodoPago().getNombreMetodo(),
                guardada.getSaldo(),
                guardada.getEsPrincipal());
    }
}