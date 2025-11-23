package com.chiflepop.demo.config;

import com.chiflepop.demo.model.*;
import com.chiflepop.demo.repository.*;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BancoRepository bancoRepository;
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    @Autowired
    private TipoBancoRepository tipoBancoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (tipoBancoRepository.count() == 0) {
            TipoBanco tipo = new TipoBanco();
            tipo.setNombreTipo("Privado");
            tipoBancoRepository.save(tipo);

            Banco bcp = new Banco();
            bcp.setNombreBanco("Banco Demo 1");
            bcp.setTipoBanco(tipo);
            bancoRepository.save(bcp);

            Banco interbank = new Banco();
            interbank.setNombreBanco("Banco Demo 2");
            interbank.setTipoBanco(tipo);
            bancoRepository.save(interbank);
        }

        if (metodoPagoRepository.count() == 0) {
            MetodoPago mp = new MetodoPago();
            mp.setNombreMetodo("Tarjeta de Crédito");
            mp.setDescripcion("Pago con TC");
            metodoPagoRepository.save(mp);
        }
        if (clienteRepository.findByEmail("admin@chiflepop.com").isEmpty()) {
            Cliente admin = new Cliente();
            admin.setNombre("Super Administrador");
            admin.setEmail("admin@chiflepop.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol("ROLE_ADMIN");
            admin.setTelefono("999000999");
            admin.setFechaRegistro(LocalDateTime.now());
            
            clienteRepository.save(admin);
            System.out.println("------------------------------------------------");
            System.out.println(">>> ADMIN CREADO: admin@chiflepop.com / admin123");
            System.out.println("------------------------------------------------");
        }
    }
}