package com.chiflepop.demo.services;

import com.chiflepop.demo.model.Cliente;
import com.chiflepop.demo.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private ClienteRepository clienteRepository;
    @Mock private PasswordEncoder passwordEncoder;


    @InjectMocks private AuthService authService;

    @Test
    @DisplayName("registrar: encripta la contraseña, asigna ROLE_CLIENTE y fecha de registro")
    void registrar_encriptaPasswordYAsignaRolPorDefecto() {
        Cliente cliente = new Cliente();
        cliente.setEmail("ana@correo.com");
        cliente.setPassword("1234");

        when(passwordEncoder.encode("1234")).thenReturn("HASH");
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Cliente resultado = authService.registrar(cliente);

        assertThat(resultado.getPassword()).isEqualTo("HASH");
        assertThat(resultado.getRol()).isEqualTo("ROLE_CLIENTE");
        assertThat(resultado.getFechaRegistro()).isNotNull();
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("registrar: si ya trae rol, no lo sobrescribe")
    void registrar_respetaRolExistente() {
        Cliente cliente = new Cliente();
        cliente.setPassword("1234");
        cliente.setRol("ROLE_ADMIN");

        when(passwordEncoder.encode("1234")).thenReturn("HASH");
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Cliente resultado = authService.registrar(cliente);

        assertThat(resultado.getRol()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    @DisplayName("login: credenciales correctas devuelven el cliente")
    void login_correcto() {
        Cliente cliente = new Cliente();
        cliente.setEmail("ana@correo.com");
        cliente.setPassword("HASH");

        when(clienteRepository.findByEmail("ana@correo.com")).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches("1234", "HASH")).thenReturn(true);

        Cliente resultado = authService.login("ana@correo.com", "1234");

        assertThat(resultado).isSameAs(cliente);
    }

    @Test
    @DisplayName("login: contraseña incorrecta devuelve null")
    void login_passwordIncorrecta() {
        Cliente cliente = new Cliente();
        cliente.setPassword("HASH");

        when(clienteRepository.findByEmail("ana@correo.com")).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches("mala", "HASH")).thenReturn(false);

        assertThat(authService.login("ana@correo.com", "mala")).isNull();
    }

    @Test
    @DisplayName("login: email inexistente devuelve null y no valida contraseña")
    void login_emailInexistente() {
        when(clienteRepository.findByEmail("nadie@correo.com")).thenReturn(Optional.empty());

        assertThat(authService.login("nadie@correo.com", "1234")).isNull();
        verifyNoInteractions(passwordEncoder);
    }
}
