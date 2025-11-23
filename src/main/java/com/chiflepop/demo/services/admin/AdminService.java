package com.chiflepop.demo.services.admin;

import com.chiflepop.demo.dto.admin.ProductoDTO;
import com.chiflepop.demo.dto.admin.PromocionDTO;
import com.chiflepop.demo.model.*;
import com.chiflepop.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private PromocionRepository promocionRepository;


    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public void eliminarCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }


    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Transactional
    public Producto guardarProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        actualizarDatosProducto(producto, dto);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(Integer id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        actualizarDatosProducto(producto, dto);
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    private void actualizarDatosProducto(Producto p, ProductoDTO dto) {
        p.setNombre(dto.nombre());
        p.setDescripcion(dto.descripcion());
        p.setPrecio(dto.precio());
        p.setStock(dto.stock());
    }

    // ================= PROMOCIONES =================

    public List<Promocion> listarPromociones() {
        return promocionRepository.findAll();
    }

    @Transactional
    public Promocion guardarPromocion(PromocionDTO dto) {
        Promocion promo = new Promocion();
        actualizarDatosPromocion(promo, dto);
        return promocionRepository.save(promo);
    }

    @Transactional
    public Promocion actualizarPromocion(Integer id, PromocionDTO dto) {
        Promocion promo = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
        actualizarDatosPromocion(promo, dto);
        return promocionRepository.save(promo);
    }

    public void eliminarPromocion(Integer id) {
        if (!promocionRepository.existsById(id)) {
            throw new RuntimeException("Promoción no encontrada");
        }
        promocionRepository.deleteById(id);
    }

    private void actualizarDatosPromocion(Promocion p, PromocionDTO dto) {
        p.setNombrePromocion(dto.nombrePromocion());
        p.setDescripcion(dto.descripcion());
        p.setDescuentoPorcentaje(dto.descuentoPorcentaje());
        p.setFechaInicio(dto.fechaInicio());
        p.setFechaFin(dto.fechaFin());
        p.setActivo(dto.activo());
    }
}