package com.chiflepop.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "DireccionesEntrega")
public class DireccionEntrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DireccionEntregaID")
    private Integer direccionEntregaId;

    @ManyToOne
    @JoinColumn(name = "ClienteID", nullable = false)
    @JsonIgnore
    private Cliente cliente;

    @Column(name = "Direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "Ciudad", length = 50)
    private String ciudad;

    @Column(name = "Departamento", length = 50)
    private String departamento;

    @Column(name = "Pais", length = 50)
    private String pais;

    @Column(name = "EsPrincipal")
    private Boolean esPrincipal;

    public Integer getDireccionEntregaId() {
        return direccionEntregaId;
    }

    public void setDireccionEntregaId(Integer direccionEntregaId) {
        this.direccionEntregaId = direccionEntregaId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}