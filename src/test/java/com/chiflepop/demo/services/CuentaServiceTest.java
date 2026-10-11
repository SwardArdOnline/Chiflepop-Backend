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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {

    @Mock private CuentaClienteRepository cuentaRepository;
    @Mock private ClienteRepository clienteRepository;
    @Mock private BancoRepository bancoRepository;
    @Mock private MetodoPagoRepository metodoPagoRepository;

    @InjectMocks private CuentaService cuentaService;

    private Banco banco;
    private MetodoPago metodo;

    @BeforeEach
    void setUp() {
        banco = new Banco();
        banco.setBancoId(1);
        banco.setNombreBanco("BCP");
        metodo = new MetodoPago();
        metodo.setMetodoPagoId(1);
        metodo.setNombreMetodo("Tarjeta");

        lenient().when(cuentaRepository.save(any(CuentaCliente.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    private CrearCuentaDTO dto() {
        return new CrearCuentaDTO(1, 1, "4111111111111111", new BigDecimal("300.00"));
    }

    @Test
    @DisplayName("crearCuenta: la primera cuenta del cliente queda como principal")
    void crearCuenta_primeraEsPrincipal() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));
        when(bancoRepository.findById(1)).thenReturn(Optional.of(banco));
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.of(metodo));
        when(cuentaRepository.findByCliente_ClienteId(1)).thenReturn(List.of());

        CuentaDTO resultado = cuentaService.crearCuenta(1, dto());

        assertThat(resultado.esPrincipal()).isTrue();
        assertThat(resultado.banco()).isEqualTo("BCP");
        assertThat(resultado.tipo()).isEqualTo("Tarjeta");
        assertThat(resultado.saldo()).isEqualByComparingTo("300.00");
    }

    @Test
    @DisplayName("crearCuenta: si ya tiene cuentas, la nueva NO es principal")
    void crearCuenta_segundaNoEsPrincipal() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));
        when(bancoRepository.findById(1)).thenReturn(Optional.of(banco));
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.of(metodo));
        when(cuentaRepository.findByCliente_ClienteId(1)).thenReturn(List.of(new CuentaCliente()));

        CuentaDTO resultado = cuentaService.crearCuenta(1, dto());

        assertThat(resultado.esPrincipal()).isFalse();
    }

    @Test
    @DisplayName("crearCuenta: cliente inexistente lanza excepción")
    void crearCuenta_clienteNoExiste() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cuentaService.crearCuenta(1, dto()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cliente no encontrado");
    }

    @Test
    @DisplayName("crearCuenta: banco inexistente lanza excepción")
    void crearCuenta_bancoNoExiste() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));
        when(bancoRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cuentaService.crearCuenta(1, dto()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Banco no encontrado");
    }

    @Test
    @DisplayName("crearCuenta: método de pago inexistente lanza excepción")
    void crearCuenta_metodoPagoNoExiste() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));
        when(bancoRepository.findById(1)).thenReturn(Optional.of(banco));
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cuentaService.crearCuenta(1, dto()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Método de pago no encontrado");
    }

    @Test
    @DisplayName("listarCuentasPorUsuario: convierte las cuentas a DTO")
    void listarCuentasPorUsuario_mapeaDTO() {
        CuentaCliente cuenta = new CuentaCliente();
        cuenta.setCuentaClienteId(7);
        cuenta.setBanco(banco);
        cuenta.setMetodoPago(metodo);
        cuenta.setNumeroCuenta("4111");
        cuenta.setSaldo(new BigDecimal("50.00"));
        cuenta.setEsPrincipal(true);
        when(cuentaRepository.findByCliente_ClienteId(1)).thenReturn(List.of(cuenta));

        List<CuentaDTO> lista = cuentaService.listarCuentasPorUsuario(1);

        assertThat(lista).hasSize(1);
        assertThat(lista.get(0).id()).isEqualTo(7);
        assertThat(lista.get(0).banco()).isEqualTo("BCP");
        assertThat(lista.get(0).esPrincipal()).isTrue();
    }
}
