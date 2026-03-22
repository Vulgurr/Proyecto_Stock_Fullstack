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