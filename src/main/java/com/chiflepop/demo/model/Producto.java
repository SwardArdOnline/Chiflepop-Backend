package com.chiflepop.demo.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductoID")
    private Integer productoId;

    @Column(name = "Nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "Precio", precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "Stock")
    private Integer stock;

    // Relaciones
    @OneToMany(mappedBy = "producto")
    private Set<DetallePedido> detalles;

    @OneToMany(mappedBy = "producto")
    private Set<ClienteProducto> clientesProductos;

    @OneToMany(mappedBy = "producto")
    private Set<EmpleadoProducto> empleadosProductos;

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Set<ClienteProducto> getClientesProductos() {
        return clientesProductos;
    }

    public void setClientesProductos(Set<ClienteProducto> clientesProductos) {
        this.clientesProductos = clientesProductos;
    }

    public Set<EmpleadoProducto> getEmpleadosProductos() {
        return empleadosProductos;
    }

    public void setEmpleadosProductos(Set<EmpleadoProducto> empleadosProductos) {
        this.empleadosProductos = empleadosProductos;
    }
    
}