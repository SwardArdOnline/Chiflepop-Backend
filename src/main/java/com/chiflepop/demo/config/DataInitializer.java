package com.chiflepop.demo.config;

import com.chiflepop.demo.model.*;
import com.chiflepop.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private BancoRepository bancoRepository;
    @Autowired private MetodoPagoRepository metodoPagoRepository;
    @Autowired private TipoBancoRepository tipoBancoRepository;

    @Override
    public void run(String... args) throws Exception {
        // 1. Crear Tipo Banco si no existe
        if (tipoBancoRepository.count() == 0) {
            TipoBanco tipo = new TipoBanco();
            tipo.setNombreTipo("Privado");
            tipoBancoRepository.save(tipo);
            
            // 2. Crear Bancos Mock
            Banco bcp = new Banco();
            bcp.setNombreBanco("Banco Demo 1");
            bcp.setTipoBanco(tipo);
            bancoRepository.save(bcp);

            Banco interbank = new Banco();
            interbank.setNombreBanco("Banco Demo 2");
            interbank.setTipoBanco(tipo);
            bancoRepository.save(interbank);
        }

        // 3. Crear Método de Pago
        if (metodoPagoRepository.count() == 0) {
            MetodoPago mp = new MetodoPago();
            mp.setNombreMetodo("Tarjeta de Crédito");
            mp.setDescripcion("Pago con TC");
            metodoPagoRepository.save(mp);
        }
    }
}