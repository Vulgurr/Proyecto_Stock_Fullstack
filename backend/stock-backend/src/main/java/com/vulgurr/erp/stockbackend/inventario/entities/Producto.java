package com.vulgurr.erp.stockbackend.inventario.entities;
import com.vulgurr.erp.stockbackend.enums.UnidadDeMedida; // Importamos el Enum que creamos recién
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "productos")
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    // Esto es lo que ve el usuario, ej: "MP2057"
    @Column(nullable = false, unique = true, length = 15)
    private String codigoArticulo;

    @Column(nullable = false, length = 100)
    private String nombre;

    private String estado;

    @Column(nullable = false)
    private Boolean estaActivo; // Boolean con mayúscula para que acepte nulls si hiciera falta

    // Campos de auditoría (súper útiles en ERPs)
    @Column(updatable = false) // No deja que se modifique después de creada
    private LocalDateTime fechaAlta;

    private LocalDateTime fechaBaja;

    private Integer stockMinimo; // Integer con mayúscula para aceptar nulls

    // JPA guardará el texto (STRING) en la DB
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadDeMedida unidadMedida;

    // TODO: La relación con Stockactual la haremos cuando creemos la entidad MovimientoStock/Lote

    @PrePersist
    protected void onCreate() {
        this.fechaAlta = LocalDateTime.now();
        if (this.estaActivo == null) {
            this.estaActivo = true; // Valor por defecto
        }
    }
    @Transient
    public void agregarProducto(Producto prod) {
        throw new UnsupportedOperationException("No se pueden agregar ingredientes a este producto.");
    }

    @Transient
    public void eliminarProducto(Producto prod) {
        throw new UnsupportedOperationException("No se pueden eliminar ingredientes de este producto.");
    }

    @Transient
    public List<Producto> obtenerReceta() {
        // Por defecto, un producto genérico no tiene receta
        return new ArrayList<Producto>();
    }

}