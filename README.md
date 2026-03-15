# Sistema de Gestión de Producción y Stock (MRP)

Este proyecto es un sistema integral diseñado para administrar la trazabilidad física y lógica del inventario en una planta de producción. Este sistema implementa lógicas de **Planificación de Requerimientos de Materiales (MRP)** y manejo de **Recetas (BOM)** para vincular la materia prima con los productos finales.

## Objetivo del Sistema
Permitir una gestión eficiente, segura y ordenada del inventario, garantizando que la fábrica tenga los insumos necesarios para operar sin generar sobrestock, y manteniendo una trazabilidad estricta de los vencimientos e ingresos.

## Características Principales
* **Proyección de Disponibilidad:** Cálculo dinámico de stock disponible basado en el inventario físico y las reservas de materias primas para órdenes futuras.
* **Gestión de Órdenes de Trabajo:** Ciclo de vida completo de la producción (Emisión, En Progreso, Finalización) con control de consumos esperados vs. reales.
* **Trazabilidad por Lotes:** Registro estricto de movimientos de stock (entradas y salidas) asociados a lotes específicos para el control de vencimientos (Kardex).
* **Control de Accesos (RBAC):** Sistema protegido por JWT con roles definidos para segmentar responsabilidades:
  * **Gerente:** Creación de recetas, aprobación de variaciones de consumo y reportes.
  * **Administrativo:** Emisión de órdenes de trabajo y control de disponibilidad.
  * **Comprador:** Ingreso de mercadería y actualización de stock físico.

## Estado del Proyecto
Actualmente, el proyecto se encuentra en la **Fase de Arquitectura y Diseño**. 

En la carpeta `/docs` se puede encontrar la documentación técnica fundacional:
1. **Contrato de API REST:** Definición exhaustiva de endpoints, verbos HTTP, códigos de estado y validaciones de negocio.
2. **Modelo de Dominio (UML):** Estructura de entidades y relaciones.
3. **Máquina de Estados:** Diagrama de transición para el ciclo de vida de las órdenes de trabajo.

*(El historial de iteraciones de diseño se encuentra documentado en el archivo `CHANGELOG_ARQUITECTURA.md`, dentro de la carpeta `/docs`)*