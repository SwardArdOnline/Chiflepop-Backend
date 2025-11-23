package com.chiflepop.demo.model;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Estados")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EstadoID")
    private Integer estadoId;

    @Column(name = "Descripcion", length = 50, nullable = false)
    private String descripcion;

    // Relaciones
    @OneToMany(mappedBy = "estado")
    private Set<Pedido> pedidos;

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
}