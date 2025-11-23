package com.chiflepop.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ClientesProductos")
public class ClienteProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClienteProductoID")
    private Integer clienteProductoId;

    @ManyToOne
    @JoinColumn(name = "ClienteID", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ProductoID", nullable = false)
    private Producto producto;

    @Column(name = "FechaRelacion")
    private LocalDateTime fechaRelacion;

    @Column(name = "Comentario", columnDefinition = "TEXT")
    private String comentario;

    public Integer getClienteProductoId() {
        return clienteProductoId;
    }

    public void setClienteProductoId(Integer clienteProductoId) {
        this.clienteProductoId = clienteProductoId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public LocalDateTime getFechaRelacion() {
        return fechaRelacion;
    }

    public void setFechaRelacion(LocalDateTime fechaRelacion) {
        this.fechaRelacion = fechaRelacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
}