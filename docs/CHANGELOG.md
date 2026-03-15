# Historial de Decisiones de Arquitectura (ADR)

Los diagramas que queden deprecados serán movidos a la carpeta `/Historial_Diagramas` y renombrados con su fecha original para mantener la trazabilidad del proceso de diseño.

---

### [15/03/2026] - Refactor del Modelo de Dominio
* **Se eliminaron:** Las clases `Disponibilidad` y `Tipo` de receta.
* **Se agregaron:** El rol de `Comprador` y la entidad `Lote` (para manejar vencimientos y mejorar la trazabilidad física).
* **Lógica actualizada:** La trazabilidad ahora se maneja mediante `Movimientos de Stock` (Kardex), delegando la disponibilidad a un cálculo dinámico para evitar atributos multivaluados.

### [14/03/2026] - Contrato de API
* Agregada la primera versión de la tabla de Endpoints (Rutas, verbos HTTP, códigos de estado y validaciones de negocio).

### [13/03/2026] - Diseño Inicial
* Agregada la primera versión del Modelo de Dominio.
* Agregada la Máquina de Estados para el ciclo de vida de las Órdenes de Trabajo.