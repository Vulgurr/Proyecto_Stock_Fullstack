package com.vulgurr.erp.stockbackend.inventario.repositories;

import com.vulgurr.erp.stockbackend.inventario.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository // Le dice a Spring que este es el componente que habla con la BD
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // ==========================================
    // 1. QUERIES AUTOMÁTICAS (Derived Queries)
    // ==========================================
    // Spring lee el nombre del method y arma el SQL solo.
    // "Buscame productos cuyo 'nombre' contenga esta palabra, ignorando mayúsculas, y paginado"
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // ==========================================
    // 2. QUERIES PERSONALIZADAS (JPQL)
    // ==========================================
    // Si necesitás algo muy complejo, podés escribir la consulta usando tus clases Java.
    // Ejemplo: Traer el catálogo paginado pero solo de los productos activos.
    @Query("SELECT p FROM Producto p WHERE p.estaActivo = true")
    Page<Producto> buscarTodosLosActivosPaginados(Pageable pageable);

}