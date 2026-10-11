package com.chiflepop.demo.services;

import com.chiflepop.demo.dto.CrearDireccionDTO;
import com.chiflepop.demo.model.Cliente;
import com.chiflepop.demo.model.DireccionEntrega;
import com.chiflepop.demo.repository.ClienteRepository;
import com.chiflepop.demo.repository.DireccionEntregaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DireccionServiceTest {

    @Mock private DireccionEntregaRepository direccionRepository;
    @Mock private ClienteRepository clienteRepository;

    @InjectMocks private DireccionService direccionService;

    private final CrearDireccionDTO dto =
            new CrearDireccionDTO("Av. Larco 123", "Lima", "Lima", "Perú", "Frente al parque");

    @Test
    @DisplayName("crearDireccion: la primera dirección queda como principal")
    void crearDireccion_primeraEsPrincipal() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));
        when(direccionRepository.findByCliente_ClienteId(1)).thenReturn(List.of());
        when(direccionRepository.save(any(DireccionEntrega.class))).thenAnswer(inv -> inv.getArgument(0));

        DireccionEntrega resultado = direccionService.crearDireccion(1, dto);

        assertThat(resultado.getEsPrincipal()).isTrue();
        assertThat(resultado.getDireccion()).isEqualTo("Av. Larco 123");
        assertThat(resultado.getCiudad()).isEqualTo("Lima");
    }

    @Test
    @DisplayName("crearDireccion: si ya tiene direcciones, la nueva NO es principal")
    void crearDireccion_siguienteNoEsPrincipal() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));
        when(direccionRepository.findByCliente_ClienteId(1)).thenReturn(List.of(new DireccionEntrega()));
        when(direccionRepository.save(any(DireccionEntrega.class))).thenAnswer(inv -> inv.getArgument(0));

        DireccionEntrega resultado = direccionService.crearDireccion(1, dto);

        assertThat(resultado.getEsPrincipal()).isFalse();
    }

    @Test
    @DisplayName("crearDireccion: cliente inexistente lanza excepción")
    void crearDireccion_clienteNoExiste() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> direccionService.crearDireccion(1, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cliente no encontrado");
    }
}
