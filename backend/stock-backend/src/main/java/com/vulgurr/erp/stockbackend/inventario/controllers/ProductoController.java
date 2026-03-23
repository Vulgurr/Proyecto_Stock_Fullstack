package com.vulgurr.erp.stockbackend.inventario.controllers;

import com.vulgurr.erp.stockbackend.inventario.entities.MateriaPrima;
import com.vulgurr.erp.stockbackend.inventario.entities.Producto;
import com.vulgurr.erp.stockbackend.inventario.entities.ProductoElaborado;
import com.vulgurr.erp.stockbackend.inventario.services.GestorProductos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Le dice a Spring: "Esta clase recibe peticiones web y devuelve JSON"
@RequestMapping("/productos") // La URL base para todos los métodos de acá adentro
public class ProductoController {
    //TODO: Falta meter el jwt
    private final GestorProductos gestorProductos;

    // Inyección del Gestor
    public ProductoController(GestorProductos gestorProductos) {
        this.gestorProductos = gestorProductos;
    }

    // ==========================================
    // GET: Obtener todos (Paginados)
    // URL: http://localhost:8080/productos?page=0&size=10
    // ==========================================
    @GetMapping
    public ResponseEntity<Page<Producto>> obtenerTodos(Pageable pageable) {
        Page<Producto> productos = gestorProductos.obtenerTodosPaginados(pageable);
        return ResponseEntity.ok(productos); // Devuelve HTTP 200 (OK)
    }

    // ==========================================
    // GET: Obtener por ID
    // URL: http://localhost:8080/productos/1
    // ==========================================
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        return gestorProductos.buscarPorId(id)
                .map(ResponseEntity::ok) // Si lo encuentra, devuelve 200 OK con el producto
                .orElse(ResponseEntity.notFound().build()); // Si no, devuelve 404 Not Found
    }

    // ==========================================
    // POST: Crear Materia Prima
    // URL: http://localhost:8080/productos/materia-prima
    // ==========================================
    @PostMapping("/materia-prima")
    public ResponseEntity<Producto> crearMateriaPrima(@RequestBody MateriaPrima nuevaMateriaPrima) {
        Producto productoCreado = gestorProductos.crearProducto(nuevaMateriaPrima);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado); // Devuelve HTTP 201 (Created)
    }

    // ==========================================
    // POST: Crear Producto elaborado
    // URL: http://localhost:8080/productos/elaborado
    // ==========================================
    @PostMapping("/elaborado")
    public ResponseEntity<Producto> crearMateriaPrima(@RequestBody ProductoElaborado nuevoProductoElaborado) {
        Producto productoCreado = gestorProductos.crearProducto(nuevoProductoElaborado);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado); // Devuelve HTTP 201 (Created)
    }

    // ==========================================
    // DELETE: Borrado Lógico
    // URL: http://localhost:8080/productos/1
    // ==========================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        try {
            gestorProductos.eliminarProducto(id);
            return ResponseEntity.noContent().build(); // HTTP 204 (No Content) - Borrado exitoso
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 si el ID no existía
        }
    }
}