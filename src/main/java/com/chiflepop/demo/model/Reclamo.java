package com.chiflepop.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reclamos")
public class Reclamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReclamoID")
    private Integer reclamoId;

    @ManyToOne
    @JoinColumn(name = "ClienteID", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "PedidoID")
    private Pedido pedido;

    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "Estado", length = 50)
    private String estado;

    @Column(name = "Fecha")
    private LocalDateTime fecha;
    // Getters y Setters
}