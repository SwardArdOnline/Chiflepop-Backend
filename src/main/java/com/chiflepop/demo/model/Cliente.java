package com.chiflepop.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClienteID")
    private Integer clienteId;

    @Column(name = "Nombre", length = 100)
    private String nombre;

    @Column(name = "Email", length = 100, unique = true)
    private String email;

    @Column(name = "Telefono", length = 20)
    private String telefono;

    @Column(name = "Direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "FechaRegistro")
    private LocalDateTime fechaRegistro;

    // Relaciones
    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos;

    @OneToMany(mappedBy = "cliente")
    private Set<DireccionEntrega> direcciones;

    @OneToMany(mappedBy = "cliente")
    private Set<Reclamo> reclamos;

    @OneToMany(mappedBy = "cliente")
    private Set<Sugerencia> sugerencias;

    @OneToMany(mappedBy = "cliente")
    private Set<CuentaCliente> cuentasCliente;

    @OneToMany(mappedBy = "cliente")
    private Set<ClienteProducto> clientesProductos;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Set<DireccionEntrega> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(Set<DireccionEntrega> direcciones) {
        this.direcciones = direcciones;
    }

    public Set<Reclamo> getReclamos() {
        return reclamos;
    }

    public void setReclamos(Set<Reclamo> reclamos) {
        this.reclamos = reclamos;
    }

    public Set<Sugerencia> getSugerencias() {
        return sugerencias;
    }

    public void setSugerencias(Set<Sugerencia> sugerencias) {
        this.sugerencias = sugerencias;
    }

    public Set<CuentaCliente> getCuentasCliente() {
        return cuentasCliente;
    }

    public void setCuentasCliente(Set<CuentaCliente> cuentasCliente) {
        this.cuentasCliente = cuentasCliente;
    }

    public Set<ClienteProducto> getClientesProductos() {
        return clientesProductos;
    }

    public void setClientesProductos(Set<ClienteProducto> clientesProductos) {
        this.clientesProductos = clientesProductos;
    }
}