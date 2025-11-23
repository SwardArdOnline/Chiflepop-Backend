package com.chiflepop.demo.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpleadoID")
    private Integer empleadoId;

    @Column(name = "Nombre", length = 100)
    private String nombre;

    @Column(name = "Cargo", length = 50)
    private String cargo;

    @Column(name = "FechaIngreso")
    private LocalDate fechaIngreso;

    @OneToMany(mappedBy = "empleado")
    private Set<EmpleadoPedido> empleadosPedidos;

    @OneToMany(mappedBy = "empleado")
    private Set<EmpleadoProducto> empleadosProductos;

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Set<EmpleadoPedido> getEmpleadosPedidos() {
        return empleadosPedidos;
    }

    public void setEmpleadosPedidos(Set<EmpleadoPedido> empleadosPedidos) {
        this.empleadosPedidos = empleadosPedidos;
    }

    public Set<EmpleadoProducto> getEmpleadosProductos() {
        return empleadosProductos;
    }

    public void setEmpleadosProductos(Set<EmpleadoProducto> empleadosProductos) {
        this.empleadosProductos = empleadosProductos;
    }
}