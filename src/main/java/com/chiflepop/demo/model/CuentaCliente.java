package com.chiflepop.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CuentasCliente")
public class CuentaCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CuentaClienteID")
    private Integer cuentaClienteId;

    @ManyToOne
    @JoinColumn(name = "ClienteID", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "MetodoPagoID", nullable = false)
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "BancoID")
    private Banco banco;

    @Column(name = "NumeroCuenta", length = 100)
    private String numeroCuenta;

    @Column(name = "EsPrincipal")
    private Boolean esPrincipal;

    @Column(name = "FechaRegistro")
    private LocalDateTime fechaRegistro;

    public Integer getCuentaClienteId() {
        return cuentaClienteId;
    }

    public void setCuentaClienteId(Integer cuentaClienteId) {
        this.cuentaClienteId = cuentaClienteId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Boolean getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
}