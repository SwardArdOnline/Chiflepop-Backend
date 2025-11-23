package com.chiflepop.demo.services;

import com.chiflepop.demo.dto.CrearDireccionDTO;
import com.chiflepop.demo.model.Cliente;
import com.chiflepop.demo.model.DireccionEntrega;
import com.chiflepop.demo.repository.ClienteRepository;
import com.chiflepop.demo.repository.DireccionEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DireccionService {

    @Autowired private DireccionEntregaRepository direccionRepository;
    @Autowired private ClienteRepository clienteRepository;

    public List<DireccionEntrega> listarDirecciones(Integer clienteId) {
        return direccionRepository.findByCliente_ClienteId(clienteId);
    }

    @Transactional
    public DireccionEntrega crearDireccion(Integer clienteId, CrearDireccionDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        DireccionEntrega direccion = new DireccionEntrega();
        direccion.setCliente(cliente);
        direccion.setDireccion(dto.direccion());
        direccion.setCiudad(dto.ciudad());
        direccion.setDepartamento(dto.departamento());
        direccion.setPais(dto.pais());
        
        boolean tieneDirecciones = !direccionRepository.findByCliente_ClienteId(clienteId).isEmpty();
        direccion.setEsPrincipal(!tieneDirecciones);

        return direccionRepository.save(direccion);
    }
}