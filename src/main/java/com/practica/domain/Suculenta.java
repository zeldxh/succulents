package com.practica.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "suculenta")
public class Suculenta implements Serializable {

    // Identificador para la serialización.
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSuculenta;

    @Column(nullable = false, length = 60)
    @NotNull
    @Size(max = 60)
    private String nombreComun;

    @Column(unique = true, nullable = false, length = 80)
    @NotNull
    @Size(max = 80)
    private String nombreCientifico;

    @Column(length = 50)
    @Size(max = 50)
    private String familia;

    @Column(length = 30)
    @Size(max = 30)
    private String colorPrincipal;

    @Column(precision = 6, scale = 2)
    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal alturaCm;

    @Column(precision = 12, scale = 2)
    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal precioEstimado;

    @Column(length = 20)
    @Size(max = 20)
    private String nivelRiego;

    @Column(length = 1024)
    @Size(max = 1024)
    private String rutaImagen;

    private boolean activo;
}
