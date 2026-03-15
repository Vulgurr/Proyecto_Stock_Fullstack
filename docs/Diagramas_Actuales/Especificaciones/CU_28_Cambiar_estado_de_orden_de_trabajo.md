# Cambiar Estado de Orden de Trabajo

**ID del CU:** CU_28
**Tipo de CU:** Transaccional
**Objetivo del CU:** Modifica el estado de una Orden de Trabajo. Si es una orden pendiente de revisión debido a consumos inusuales permite aprobarla o rechazarla. Si es una orden que aún no se pasó a producción, permite abortarla.
**Actor principal:** Gerente
**Actor secundario:** No aplica

### Precondiciones
* El actor debe tener sesión iniciada 
* Debe existir al menos una orden de trabajo en el sistema.

### Reglas de Negocio
* **Transiciones permitidas:** El cambio de estado debe respetar estrictamente la jerarquía y el flujo definido en el diagrama de la Máquina de Estados de la Orden de Trabajo.
* **Restricción de Cancelación:** Una orden solo puede pasar a estado `CANCELADA` si actualmente se encuentra en estado `EN_ESPERA`.

### Flujo Normal
  1. El actor inicia el caso de uso
  2. El sistema le solicita un ID de orden de trabajo para modificar su estado mediante una lista desplegable.
  3. El actor selecciona el ID de la orden de trabajo seleccionada.
  4. El sistema muestra la orden de trabajo seleccionada junto con un botón para modificar su estado.
  5. El actor oprime "Modificar Estado" y oprime el estado que desea aplicarle (Aprobada/Rechazada/Abortada).
  6. El sistema verifica que la transición sea válida según las Reglas de Negocio.
  7. El sistema actualiza el estado de la orden. Si el estado implica una Aprobación de consumos inusuales, el sistema procesa la deducción pendiente impactando el stock físico/real. Si el estado implica una cancelación (Rechazada/Abortada), el sistema libera la reserva, devolviéndola al stock proyectado.
  8. El sistema muestra un mensaje de éxito y finaliza el caso de uso.

### Flujos Alternativos

#### A0 Cancelación
En cualquier paso antes del paso 5
  1. **\*.1:** El actor oprime "Cancelar" y finaliza el caso de uso  

#### A1 Modificación inválida
  1. **6.1:** El sistema detecta que el actor intenta modificar una orden que no puede ser modificada (Por ejemplo, por estar en estado `FINALIZADA`) o que el actor no tiene permisos para hacer.
  2. **6.2:** El sistema muestra un mensaje de error y finaliza el caso de uso


### Poscondiciones
* El estado de la orden de trabajo queda actualizado en la base de datos.
* Si se aprobó un desvío, se actualiza el stock real y se generan los movimientos de Kardex. Si se canceló, se recalcula el stock proyectado liberando los insumos.