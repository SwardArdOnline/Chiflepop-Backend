package com.chiflepop.demo.model;
import jakarta.persistence.*;

@Entity
@Table(name = "EmpleadosPedidos")
public class EmpleadoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpleadoPedidoID")
    private Integer empleadoPedidoId;

    @ManyToOne
    @JoinColumn(name = "EmpleadoID", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "PedidoID", nullable = false)
    private Pedido pedido;

    @Column(name = "RolEnPedido", length = 50)
    private String rolEnPedido;

    public Integer getEmpleadoPedidoId() {
        return empleadoPedidoId;
    }

    public void setEmpleadoPedidoId(Integer empleadoPedidoId) {
        this.empleadoPedidoId = empleadoPedidoId;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getRolEnPedido() {
        return rolEnPedido;
    }

    public void setRolEnPedido(String rolEnPedido) {
        this.rolEnPedido = rolEnPedido;
    }
    
}