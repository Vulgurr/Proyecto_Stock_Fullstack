# Historial de Decisiones de Arquitectura (ADR)

Los diagramas que queden deprecados serán movidos a la carpeta `/Historial_Diagramas` y renombrados con su fecha original para mantener la trazabilidad del proceso de diseño.


---

### [21/03/2026] - Diseño Físico de Base de Datos (DER)
* **Se agregaron (Arquitectura de Datos):** * **Diagrama Entidad-Relación (DER):** Mapeo físico del modelo de clases a un esquema relacional SQL normalizado. Se definieron claves primarias (PK), foráneas (FK) y tablas intermedias (`ItemReceta`, `DetalleCompra`, `Consumo`) para garantizar la integridad referencial.
* **Lógica actualizada:** * **Estrategia de Mapeo Objeto-Relacional:** Se implementó la estrategia *Single Table* para persistir la herencia del patrón Composite en la tabla `Productos`. Además, se consolidó el motor de trazabilidad conectando los `Consumos` reales de producción directamente con los `Lotes` físicos, permitiendo el cálculo automático de mermas y desvíos (Planificado vs. Real).

### [20/03/2026] - Arquitectura del Sistema y Diagrama de Clases Integral
* **Se agregaron:** * **Diagrama de Clases UML completo:** Mapeo integral del dominio del sistema estructurado bajo una arquitectura de tres capas estrictas (Controladores REST, Gestores de Negocio y Repositorios de Datos) para asegurar alta cohesión y bajo acoplamiento.
* **Lógica actualizada:** * **Patrones de Diseño Implementados:** Integración estructural del patrón **Composite** para la jerarquía de productos abstractos (`MateriaPrima` y `ProductoElaborado`), y del patrón **State** para la mutación segura de las Órdenes de Trabajo. Adicionalmente, se modeló la segregación de dominios (compras vs. producción) y la orquestación automática de eventos temporales mediante `<<CronJob>>`.

### [19/03/2026] - Refactor de Máquina de Estados y Lógica de Producción
* **Lógica actualizada:** * **Diagrama de Estados (Orden de Trabajo):** Se rediseñó el ciclo de vida incorporando la validación cruzada estricta entre **Stock Proyectado** (para la fase de planificación) y **Stock Real** (para la habilitación de ejecución).
* **Se agregaron (Nuevos Estados y Flujos):** * Transiciones condicionales y estados de control como `PENDIENTE DE APROBACION` para auditar desvíos y mermas entre el consumo estimado vs. real y `FIN TEMPRANO` para liberación de stock remanente. 

### [16/03/2026] - Gestión de Lotes, Trazabilidad y Refactor de Documentación
* **Se agregaron (Casos de Uso):** * Nuevos flujos al Glosario y al Diagrama UML para el ciclo de vida del inventario: `CU-30 Consultar Trazabilidad de Lote`, `CU-31 Registrar Ingreso de Lote` y `CU-32 Ajustar/Dar de Baja Lote`.
* **Se agregaron (Contrato de API):** * Definición del recurso `/lotes` con endpoints para listar, consultar trazabilidad (Kardex), registrar ingresos y realizar ajustes manuales de stock.
* **Lógica actualizada:** * **Refactor del CU-29 (Cargar consumos):** Se integró la política de consumo en cascada con prioridad **FEFO** (First Expires, First Out). El sistema ahora pre-completa sugerencias de lotes de forma inteligente, permitiendo al usuario el control final (override) para garantizar la trazabilidad física real.

### [15/03/2026] - Casos de Uso
* **Se agregaron:** 
    * Diagrama de Casos de Uso 
    * Glosario de Casos de Uso.
    * Especificaciones para los Casos de Uso core del sistema: Planificación, Gestión de Estados y Carga de Consumos Reales.
* **Hitos:** Definición de la lógica de Stock Proyectado vs. Stock Real y validación de márgenes de tolerancia en producción.

### [15/03/2026] - Refactor del Modelo de Dominio
* **Se eliminaron:** Las clases `Disponibilidad` y `Tipo` de receta.
* **Se agregaron:** El rol de `Comprador` y la entidad `Lote` (para manejar vencimientos y mejorar la trazabilidad física).
* **Lógica actualizada:** La trazabilidad ahora se maneja mediante `Movimientos de Stock` (Kardex), delegando la disponibilidad a un cálculo dinámico para evitar atributos multivaluados.

### [14/03/2026] - Contrato de API
* Agregada la primera versión de la tabla de Endpoints (Rutas, verbos HTTP, códigos de estado y validaciones de negocio).

### [13/03/2026] - Diseño Inicial
* Agregada la primera versión del Modelo de Dominio.
* Agregada la Máquina de Estados para el ciclo de vida de las Órdenes de Trabajo.