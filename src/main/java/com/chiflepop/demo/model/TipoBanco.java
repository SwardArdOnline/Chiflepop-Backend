package com.chiflepop.demo.model;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TipoBanco")
public class TipoBanco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TipoBancoID")
    private Integer tipoBancoId;

    @Column(name = "NombreTipo", length = 50, nullable = false, unique = true)
    private String nombreTipo;

    // Relaciones
    @OneToMany(mappedBy = "tipoBanco")
    private Set<Banco> bancos;

    public Integer getTipoBancoId() {
        return tipoBancoId;
    }

    public void setTipoBancoId(Integer tipoBancoId) {
        this.tipoBancoId = tipoBancoId;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public Set<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(Set<Banco> bancos) {
        this.bancos = bancos;
    }
    
}