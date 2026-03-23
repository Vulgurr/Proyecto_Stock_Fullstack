# Guía de Anotaciones y Configuración Inicial (Spring Boot)

Esta es una bitácora de los conceptos aplicados en el Sprint 1 para configurar el ORM y la base de datos PostgreSQL.

## 1. Anotaciones de Lombok
Lombok es una librería que nos ahorra escribir código repetitivo (boilerplate) en tiempo de compilación.

* **`@Data`**: Se pone arriba de la clase. Genera automáticamente por detrás todos los `getters`, `setters`, constructores requeridos, `toString()`, etc.
* **`@EqualsAndHashCode(callSuper = true)`**: Se usa en las **clases hijas**. Le avisa a Lombok que al comparar dos objetos, también debe tener en cuenta los atributos heredados de la clase padre.

## 2. Anotaciones de JPA / Hibernate (Bases de Datos)
Estas anotaciones (del paquete `jakarta.persistence`) mapean nuestras clases Java a tablas reales en la base de datos relacional.

### Básicas de Entidad
* **`@Entity`**: Le indica a Spring Boot que esta clase representa una tabla en la base de datos.
* **`@Table(name = "nombre_en_plural")`**: Permite forzar un nombre específico para la tabla en la DB.

### Columnas y Claves
* **`@Id`**: Define que el atributo debajo de esta anotación será la Clave Primaria (Primary Key).
* **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**: Hace que el ID sea autoincremental (el equivalente al `SERIAL` en Postgres).
* **`@Column(...)`**: Permite configurar restricciones a nivel de base de datos. Ejemplos de parámetros:
    * `nullable = false`: Equivale a un NOT NULL.
    * `length = 100`: Limita el VARCHAR a 100 caracteres.
    * `updatable = false`: Evita que el valor se modifique en un UPDATE posterior (ideal para fechas de alta).

### Tipos de Datos Especiales
* **`@Enumerated(EnumType.STRING)`**: Se usa sobre los atributos tipo `Enum`. Obliga a la base de datos a guardar el valor como texto (ej: "KILOS") en lugar de un número (0, 1), lo cual es mucho más legible.
* **`@Transient`**: Le dice al ORM que ignore este atributo/método. No creará una columna en la base de datos para esto (útil para campos calculados o placeholders).

### Herencia Profesional (Estrategia JOINED)
Para mapear diagramas UML donde hay clases abstractas e hijas:
* **`@Inheritance(strategy = InheritanceType.JOINED)`**: Se coloca en la **clase Padre**. Crea una tabla base para el padre y tablas individuales para cada hijo, vinculándolas por ID.
* **`@PrimaryKeyJoinColumn(name = "id_producto")`**: Se coloca en las **clases Hijas**. Indica explícitamente que no tendrán un ID autogenerado propio, sino que usarán el ID de la tabla padre como PK y FK al mismo tiempo.

### Ciclo de Vida (Triggers de Java)
* **`@PrePersist`**: Se coloca sobre un método. Ejecuta la lógica de ese método justo un milisegundo antes de hacer el `INSERT` en la base de datos (ideal para setear fechas automáticas o valores por defecto).

---

## 3. El Archivo `application.properties`
Ubicado en `src/main/resources/`, es el centro de comando de Spring Boot.

* **Funciona como un archivo de entorno (`.env`):** Almacena las credenciales de la base de datos usando placeholders como `${DB_USER}` para no subir secretos al repositorio. Los valores reales se inyectan localmente mediante las *Environment Variables* del entorno de ejecución (Run Configuration en IntelliJ).
* **Configura el Framework:** Le da instrucciones directas a las herramientas internas. Por ejemplo:
    * `spring.jpa.hibernate.ddl-auto=update`: Permite que Hibernate lea nuestras entidades (`@Entity`) y cree/modifique las tablas automáticamente sin escribir SQL manual.
    * `spring.jpa.show-sql=true`: Imprime en la consola las queries SQL que Spring genera por detrás, facilitando el debugging.

# Guía de Spring Data JPA: Repositorios, Paginación y Consultas

En Spring Boot, la capa de acceso a datos (Data Access Layer) no requiere que escribamos clases complejas con conexiones manuales a la base de datos. Se maneja todo a través de **Interfaces**.

## 1. El Repositorio (`JpaRepository`)
Para conectar una entidad (como `Producto`) a su tabla en la base de datos, creamos una interfaz que herede de `JpaRepository<TipoEntidad, TipoID>`.

* **¿Qué hace?** En tiempo de ejecución, Spring Boot crea automáticamente una clase que implementa esta interfaz y le inyecta todo el código SQL necesario por detrás.
* **El CRUD gratis:** Al extender de `JpaRepository`, heredamos de forma automática métodos listos para usar como:
  * `save(entidad)`: Inserta o actualiza un registro.
  * `findById(id)`: Busca un registro por su clave primaria.
  * `findAll()`: Trae todos los registros.
  * `deleteById(id)`: Elimina un registro.

## 2. Paginación: `Pageable` vs `Page<T>`

Cuando una tabla crece (ej: 10.000 productos), usar `findAll()` colapsa la memoria. Spring resuelve esto de forma nativa con dos objetos clave: uno para "pedir" y otro para "recibir".

### A. El pedido: `Pageable` (Lo que enviamos a la BD)
Es una interfaz que contiene las instrucciones de paginación que van a viajar hacia la base de datos (se traduce a los comandos `LIMIT` y `OFFSET` en SQL).
* Se crea desde los controladores o gestores usando: `PageRequest.of(numeroDePagina, tamañoDePagina)`.
* Ejemplo: `PageRequest.of(0, 10)` le dice a Postgres: "Traeme los primeros 10 resultados empezando desde el índice 0".
* También permite inyectar reglas de ordenamiento (ej: ordenar por precio de menor a mayor).

### B. La respuesta: `Page<T>` (Lo que nos devuelve la BD)
No es una simple lista (`List<T>`). Es un objeto enriquecido que contiene "la porción" de datos que pedimos, pero además incluye metadatos vitales para que el Frontend pueda armar la botonera de paginación (1, 2, 3, Siguiente...).
* Contiene métodos utilísimos como:
  * `.getContent()`: Extrae la lista real de objetos (ej: los 10 productos).
  * `.getTotalElements()`: Devuelve cuántos registros hay en total en toda la tabla.
  * `.getTotalPages()`: Devuelve la cantidad total de páginas disponibles.
  * `.hasNext()` / `.hasPrevious()`: Devuelve un booleano indicando si hay página siguiente o anterior.

## 3. Consultas Personalizadas (Queries)
Si los métodos del CRUD básico no alcanzan, Spring ofrece dos formas de hacer consultas a medida:

### A. Derived Queries (Consultas Derivadas)
Spring es capaz de leer el nombre del método que escribimos en la interfaz y traducirlo a SQL automáticamente, siempre que respetemos su vocabulario en inglés (`findBy`, `Containing`, `GreaterThan`, etc.).
* **Ejemplo:** `Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);`
* **Traducción SQL:** `SELECT * FROM productos WHERE ILIKE %nombre% LIMIT X OFFSET Y`.

### B. JPQL con `@Query`
Cuando la consulta es muy compleja (ej: cruzar varias tablas o filtrar por muchas condiciones), escribimos la consulta a mano usando la anotación `@Query`.
* **La gran diferencia:** No usamos código SQL nativo, sino **JPQL (Java Persistence Query Language)**.
* En JPQL, consultamos a las **Clases de Java** y a sus **Atributos**, NO a las tablas y columnas de Postgres.
* **Ejemplo:** `@Query("SELECT p FROM Producto p WHERE p.estaActivo = true")` (Fijate que usamos `Producto` en mayúscula como la clase, no `productos` como la tabla).