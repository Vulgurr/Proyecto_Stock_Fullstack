package com.vulgurr.erp.stockbackend.inventario.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) // Necesario para Lombok en herencia
@Entity
@Table(name = "materias_primas")
@PrimaryKeyJoinColumn(name = "id_producto")
public class MateriaPrima extends Producto {
}