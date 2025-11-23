package com.chiflepop.demo.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PedidoID")
    private Integer pedidoId;

    @ManyToOne
    @JoinColumn(name = "ClienteID", nullable = false)
    private Cliente cliente;

    @Column(name = "FechaPedido")
    private LocalDateTime fechaPedido;

    @ManyToOne
    @JoinColumn(name = "EstadoID", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "pedido")
    private Set<DetallePedido> detalles;

    @OneToMany(mappedBy = "pedido")
    private Set<EmpleadoPedido> empleadosPedidos;

    @OneToMany(mappedBy = "pedido")
    private Set<Reclamo> reclamos;
    @Column(name = "Total", precision = 10, scale = 2)
    private BigDecimal total;
    @ManyToOne
    @JoinColumn(name = "DireccionEntregaID", nullable = false)
    private DireccionEntrega direccionEntrega;

    @ManyToOne
    @JoinColumn(name = "CuentaClienteID", nullable = false)
    private CuentaCliente cuentaCliente;
    
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public DireccionEntrega getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(DireccionEntrega direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public CuentaCliente getCuentaCliente() {
        return cuentaCliente;
    }

    public void setCuentaCliente(CuentaCliente cuentaCliente) {
        this.cuentaCliente = cuentaCliente;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Set<EmpleadoPedido> getEmpleadosPedidos() {
        return empleadosPedidos;
    }

    public void setEmpleadosPedidos(Set<EmpleadoPedido> empleadosPedidos) {
        this.empleadosPedidos = empleadosPedidos;
    }

    public Set<Reclamo> getReclamos() {
        return reclamos;
    }

    public void setReclamos(Set<Reclamo> reclamos) {
        this.reclamos = reclamos;
    }
    
}