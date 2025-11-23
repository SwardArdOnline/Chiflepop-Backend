package com.chiflepop.demo.services;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private BancoRepository bancoRepository;
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CuentaClienteRepository cuentaClienteRepository;

    @Transactional
    public Cliente registrar(Cliente cliente) {
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        if (cliente.getRol() == null)
            cliente.setRol("ROLE_CLIENTE");
        cliente.setFechaRegistro(LocalDateTime.now());
        Cliente clienteGuardado = clienteRepository.save(cliente);

        // 2. Generar Tarjetas Automáticas (Solo si es cliente)
        if ("ROLE_CLIENTE".equals(cliente.getRol())) {
            asignarTarjetasDemo(clienteGuardado);
        }

        return clienteGuardado;
    }
    private void crearTarjeta(Cliente cliente, Banco banco, MetodoPago metodoPago, BigDecimal saldo, boolean esPrincipal) {
        CuentaCliente cuenta = new CuentaCliente();
        cuenta.setCliente(cliente);
        cuenta.setBanco(banco);
        cuenta.setMetodoPago(metodoPago);
        cuenta.setSaldo(saldo);
        cuenta.setEsPrincipal(esPrincipal);
        cuenta.setFechaRegistro(LocalDateTime.now());
        
        cuenta.setNumeroCuenta(generarNumeroTarjetaFalso());

        cuentaClienteRepository.save(cuenta);
    }
    private void asignarTarjetasDemo(Cliente cliente) {
        Banco bancoPrincipal = bancoRepository.findById(1).orElse(null); 
        Banco bancoSecundario = bancoRepository.findById(2).orElse(null);
        
        MetodoPago metodoPago = metodoPagoRepository.findById(1).orElse(null);

        if (metodoPago == null) return;

        if (bancoPrincipal != null) {
            crearTarjeta(cliente, bancoPrincipal, metodoPago, new BigDecimal("500.00"), true);
        }
        if (bancoSecundario != null) {
            crearTarjeta(cliente, bancoSecundario, metodoPago, new BigDecimal("500.00"), false);
        }
    }
    private String generarNumeroTarjetaFalso() {
        Random random = new Random();
        return "4" + String.format("%015d", Math.abs(random.nextLong()) % 1000000000000000L);
    }

    public Cliente login(String email, String passwordRaw) {
        Optional<Cliente> usuarioOpt = clienteRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Cliente usuario = usuarioOpt.get();
            if (passwordEncoder.matches(passwordRaw, usuario.getPassword())) {
                return usuario;
            }
        }
        return null;
    }
}