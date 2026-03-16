# Glosario de Casos de Uso

A continuación se detallan las descripciones funcionales breves de los casos de uso del sistema, agrupados por módulo de negocio.

## Módulo de Seguridad y Accesos
* **CU-01 Iniciar Sesión:** Permite a un Usuario Invitado ingresar sus credenciales (correo y contraseña). Si son válidas, el sistema le concede acceso a las funcionalidades correspondientes a su rol.
* **CU-02 Solicitar Cambio de Contraseña:** Permite a un Usuario Invitado solicitar la recuperación de su cuenta mediante el envío de un enlace o código al correo electrónico registrado.

## Módulo de Gestión de Usuarios y Perfiles
* **CU-03 Crear Rol:** Permite al Administrador dar de alta un nuevo rol dentro del sistema para agrupar niveles de acceso.
* **CU-04 Asignar Permisos a Rol:** Permite al Administrador configurar y delimitar qué acciones específicas (permisos) puede ejecutar cada rol.
* **CU-05 Crear Cuenta:** Permite al Administrador registrar a un nuevo empleado en el sistema ingresando sus datos básicos, asignándole un rol y generando una contraseña inicial automática.
* **CU-06 Dar de Baja Cuenta:** Permite al Administrador realizar una baja lógica de un usuario (por despido o renuncia), revocando su acceso sin eliminar su historial de operaciones.
* **CU-07 Cambiar Contraseña:** Permite al Usuario Autenticado actualizar su clave de acceso. El sistema valida las políticas de seguridad y confirma que la nueva clave no sea idéntica a la anterior.
* **CU-08 Cambiar Email:** Permite al Usuario Autenticado modificar su correo de contacto, requiriendo su contraseña actual y verificando la validez del nuevo correo mediante un mensaje de confirmación.
* **CU-09 Cambiar Datos del Perfil:** Permite al Usuario Autenticado actualizar su información personal (nombre, apellido, teléfono), exceptuando credenciales críticas de acceso.
* **CU-10 Obtener Datos de Usuario:** Permite a un Usuario Autenticado visualizar el perfil básico de otro miembro del sistema en modo de solo lectura.

## Módulo de Inventario y Compras
* **CU-11 Ver Disponibilidad de Stock:** Permite consultar la disponibilidad proyectada de materiales para una fecha específica o un rango temporal, con opción de filtrado por ID de producto.
* **CU-12 Ver Compras Realizadas:** Permite visualizar el historial de ingresos de mercadería, aplicando filtros por fecha, ID de producto o comprador responsable.
* **CU-13 Cargar Compra:** Permite al Comprador registrar el ingreso de uno o más productos al sistema, generando el movimiento de stock (Kardex) correspondiente.
* **CU-14 Actualizar Datos de Compra:** Permite al Comprador ajustar los registros de un ingreso previo para reflejar la realidad física (ej. corrigiendo la cantidad real recibida o la fecha exacta de llegada).
* **CU-15 Enviar Alerta de Reposición:** Proceso del sistema que detecta si el stock físico de un producto cruza su umbral mínimo y notifica automáticamente por correo al área de Compras.

## Módulo de Catálogo y Productos
* **CU-16 Ver Productos:** Permite visualizar el maestro de artículos (insumos y productos finales) y sus características, con capacidad de búsqueda por nombre.
* **CU-17 Crear Producto:** Permite registrar un nuevo artículo en el sistema definiendo sus parámetros base (ID, descripción, unidad de medida, cantidad inicial y umbral mínimo de reposición).
* **CU-18 Modificar Producto:** Permite actualizar las características de un artículo existente o aplicarle una baja lógica (deshabilitación) para evitar su uso futuro.
* **CU-19 Eliminar Producto:** Permite la eliminación física de un artículo del sistema, validando estrictamente que no posea dependencias (ej. no formar parte de ninguna receta activa).
* **CU-30 Consultar Trazabilidad de Lote:** Permite al Gerente o Administrativo buscar un lote específico (por código o por producto) para visualizar su cantidad física actual, su fecha de vencimiento y el historial de todos los movimientos (Kardex) asociados a ese lote.
* **CU-31 Registrar Ingreso de Lote:** Permite al Comprador (o Administrativo) registrar la entrada física de nueva mercadería al almacén, creando un lote en el sistema con su cantidad inicial, código de proveedor y fecha de vencimiento.
* **CU-32 Ajustar/Dar de Baja Lote:** Permite al Gerente realizar un ajuste de inventario manual para descontar stock de un lote específico o darlo de baja completamente (por ejemplo, por vencimiento, contaminación o merma en el almacén).

## Módulo de Producción y Recetas (BOM)
* **CU-20 Ver Recetas:** Permite explorar el listado de fórmulas de producción y desglosar su composición en subniveles de ingredientes.
* **CU-21 Crear Receta:** Permite definir la estructura de fabricación de un producto final, detallando qué insumos lo componen y en qué proporciones exactas.
* **CU-22 Modificar Receta:** Permite alterar la fórmula, rendimientos o descripción de una receta. El sistema bloquea esta acción si la receta ya posee un historial de uso en órdenes de trabajo para mantener la trazabilidad.
* **CU-23 Deshabilitar Receta:** Permite realizar una baja lógica de una fórmula para evitar que sea seleccionada en nuevas órdenes de trabajo, manteniendo su registro histórico.
* **CU-24 Eliminar Receta:** Permite borrar físicamente una receta, validando que jamás haya sido utilizada en el área de producción.
* **CU-25 Ver Órdenes de Trabajo:** Permite monitorear el listado y estado de los lotes de producción planificados o en curso, filtrando por fecha y estado de avance.
* **CU-26 Finalizar Orden de Trabajo:** Permite al Gerente concluir una orden de producción antes de su finalización planificada, registrando el ingreso al stock de la cantidad parcial fabricada y asentando el consumo real de los insumos utilizados hasta el momento de la interrupción.