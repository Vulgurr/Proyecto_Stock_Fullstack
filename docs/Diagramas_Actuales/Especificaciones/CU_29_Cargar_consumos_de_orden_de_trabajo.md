# Cargar consumos de Orden de Trabajo

**ID del CU:** CU_29
**Tipo de CU:** Core Business
**Objetivo del CU:** El actor carga los detalles de consumo reales de la orden de trabajo ya finalizada en producción, y el sistema contrasta estos valores con los esperados.
**Actor principal:** Administrativo
**Actor secundario:** No aplica
**Punto de Extensión:** `CU-15 Enviar Alerta de Reposición`
### Precondiciones
* El actor debe tener sesión iniciada 
* Debe existir al menos una orden de trabajo en el sistema que esté en el estado `INICIADA` y haya finalizado en producción.

### Reglas de Negocio
* **Prioridad para la elección de lotes:** El negocio implementa FEFO (First Expires, First Out) para la prioridad de selección de los lotes de manera automática

### Flujo Normal
  1. El actor inicia el caso de uso
  2. El sistema carga una lista desplegable de todas las órdenes de Trabajo en estado `INICIADA`.
  3. El actor selecciona la órden que quiere cargar.
  4. El sistema muestra una lista de todos los productos utilizados para realizar la receta indicada en la órden, sus cantidades esperadas y solicita las cantidades reales. Además, el sistema calcula. sugiere y pre-completa la cantidad de lotes necesarios para satisfacer las cantidades de acuerdo a las Reglas del Negocio.
  5. Para cada producto en la lista, el actor ingresa la cantidad real de insumos utilizados, los lotes de dichos insumos y presiona "Confirmar".
  6. El sistema verifica que los insumos utilizados se encuentren en un rango aceptable definido previamente.
  7. El sistema actualiza el stock real en la base de datos, la cantidad actual de cada lote y actualiza el estado de la orden a `FINALIZADA`.
  8. El sistema muestra un mensaje de éxito y finaliza el caso de uso.

### Flujos Alternativos

#### A0 Cancelación
En cualquier paso antes del paso 5
  1. **\*.1:** El actor oprime "Cancelar" y finaliza el caso de uso  

#### A1 Cantidades reales no coinciden con las estimadas
  1. **6.1:** El sistema detecta que en uno o más insumos, la cantidad real de stock no coincide con la esperada, siendo mayor o menos a los márgenes configurados.
  2. **6.2:** El sistema notifica al usuario con un mensaje y pasa la orden al estado `PENDIENTE_DE_APROBACION`, finalizando el caso de uso. 

#### A2 Stock debajo del mínimo
  1. **7.1:** El sistema detecta que en uno o más insumos, la cantidad real está por debajo del mínimo configurado.
  2. **7.2:** El sistema extiende su funcionalidad en el Caso de Uso `CU-15 Enviar Alerta de Reposición` y continúa en el paso 8 del flujo normal.

### Poscondiciones
* Los consumos reales quedan registrados y el Kardex físico se actualiza.
* Las reservas de stock proyectado se liberan.
* El estado de la orden de trabajo avanza a `FINALIZADA` (o queda `PENDIENTE_DE_APROBACION` si hubo desvíos).