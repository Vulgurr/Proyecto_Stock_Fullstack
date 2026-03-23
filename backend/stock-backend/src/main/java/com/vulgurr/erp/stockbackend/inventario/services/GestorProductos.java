package com.vulgurr.erp.stockbackend.inventario.services;

import com.vulgurr.erp.stockbackend.inventario.entities.MateriaPrima;
import com.vulgurr.erp.stockbackend.inventario.entities.Producto;
import com.vulgurr.erp.stockbackend.inventario.repositories.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service // Le dice a Spring: "Esta clase es un Gestor con lógica de negocio"
public class GestorProductos {

    // @LíderTécnico: Inyección de dependencias por Constructor (Mejor práctica).
    // Evitamos usar @Autowired en el atributo directamente porque esto hace que el código sea más fácil de testear.
    private final ProductoRepository productoRepository;

    public GestorProductos(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // ==========================================
    // LECTURA (Consultas)
    // ==========================================

    public Page<Producto> obtenerTodosPaginados(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // ==========================================
    // ESCRITURA (Transacciones)
    // ==========================================

    @Transactional // Si algo falla a la mitad de este método, revierte los cambios en la BD
    public Producto crearProducto(Producto nuevoProducto) {
        // Lógica de negocio 1: Generar el código visual automáticamente si no viene
        if (nuevoProducto.getCodigoArticulo() == null || nuevoProducto.getCodigoArticulo().isEmpty()) {
            nuevoProducto.setCodigoArticulo(generarCodigoUnico(nuevoProducto));
        }

        // Lógica de negocio 2: Asegurar que nazca activo
        nuevoProducto.setEstaActivo(true);

        return productoRepository.save(nuevoProducto);
    }

    @Transactional
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        // Buscamos si existe. Si no, lanzamos un error que luego el Controlador atajará.
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Actualizamos solo los campos permitidos (No tocamos el ID ni la fecha de alta)
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setEstado(productoActualizado.getEstado());
        productoExistente.setStockMinimo(productoActualizado.getStockMinimo());
        productoExistente.setUnidadMedida(productoActualizado.getUnidadMedida());

        return productoRepository.save(productoExistente);
    }

    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // @LíderTécnico: SOFT DELETE (Borrado Lógico)
        // En un ERP NUNCA borramos un producto de la base de datos (DELETE de SQL),
        // porque romperíamos el historial de facturas y movimientos de stock.
        // Simplemente lo "apagamos".
        producto.setEstaActivo(false);
        producto.setFechaBaja(LocalDateTime.now());

        productoRepository.save(producto);
    }

    // ==========================================
    // MÉTODOS PRIVADOS (Reglas de negocio internas)
    // ==========================================

    private String generarCodigoUnico(Producto producto) {
        // Genera un código random de 6 caracteres
        String randomStr = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        // Le pone un prefijo dependiendo de qué clase hija sea (Aprovechando el polimorfismo)
        if (producto instanceof MateriaPrima) {
            return "MP-" + randomStr;
        } else {
            return "PE-" + randomStr; // Producto Elaborado
        }
    }
}