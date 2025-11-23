package com.chiflepop.demo.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Bancos")
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BancoID")
    private Integer bancoId;

    @Column(name = "NombreBanco", length = 100, nullable = false)
    private String nombreBanco;

    @ManyToOne
    @JoinColumn(name = "TipoBancoID")
    private TipoBanco tipoBanco;

    // Relaciones
    @OneToMany(mappedBy = "banco")
    private Set<CuentaCliente> cuentasCliente;

    public Integer getBancoId() {
        return bancoId;
    }

    public void setBancoId(Integer bancoId) {
        this.bancoId = bancoId;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public TipoBanco getTipoBanco() {
        return tipoBanco;
    }

    public void setTipoBanco(TipoBanco tipoBanco) {
        this.tipoBanco = tipoBanco;
    }

    public Set<CuentaCliente> getCuentasCliente() {
        return cuentasCliente;
    }

    public void setCuentasCliente(Set<CuentaCliente> cuentasCliente) {
        this.cuentasCliente = cuentasCliente;
    }
    
}