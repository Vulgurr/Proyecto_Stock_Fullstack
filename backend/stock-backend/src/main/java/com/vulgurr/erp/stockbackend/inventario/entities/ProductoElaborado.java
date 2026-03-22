package com.vulgurr.erp.stockbackend.inventario.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) // Necesario para Lombok en herencia
@Entity
@Table(name = "productos_elaborados")
@PrimaryKeyJoinColumn(name = "id_producto")
public class ProductoElaborado extends Producto {
    // Transient le dice a JPA: "Ignorá este método, no intentes crear una columna para él"
    @Transient
    public void getRecetaPlaceholder() {
        // TODO: Este método se implementará cuando declaremos la entidad Receta en otro Sprint
        System.out.println("Aquí iría la lógica para obtener la receta");
    }
    // TODO: (Sprint 2) Implementar la relación con la entidad Receta
    // @OneToOne
    // @JoinColumn(name = "id_receta")
    // private Receta receta;
}