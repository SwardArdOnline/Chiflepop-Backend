package com.chiflepop.demo.model;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MetodoPago")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MetodoPagoID")
    private Integer metodoPagoId;

    @Column(name = "NombreMetodo", length = 50, nullable = false, unique = true)
    private String nombreMetodo;

    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @OneToMany(mappedBy = "metodoPago")
    private Set<CuentaCliente> cuentasCliente;

    public Integer getMetodoPagoId() {
        return metodoPagoId;
    }

    public void setMetodoPagoId(Integer metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<CuentaCliente> getCuentasCliente() {
        return cuentasCliente;
    }

    public void setCuentasCliente(Set<CuentaCliente> cuentasCliente) {
        this.cuentasCliente = cuentasCliente;
    }


}