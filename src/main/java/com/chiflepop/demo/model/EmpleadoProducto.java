package com.chiflepop.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "EmpleadoProducto")
public class EmpleadoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpleadoProductoID")
    private Integer empleadoProductoId;

    @ManyToOne
    @JoinColumn(name = "EmpleadoID", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "ProductoID", nullable = false)
    private Producto producto;

    @Column(name = "Función", length = 100)
    private String funcion;

    public Integer getEmpleadoProductoId() {
        return empleadoProductoId;
    }

    public void setEmpleadoProductoId(Integer empleadoProductoId) {
        this.empleadoProductoId = empleadoProductoId;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
}