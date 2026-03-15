# Crear Orden de Trabajo

**ID del CU:** CU_30
**Tipo de CU:** Core Business
**Objetivo del CU:** Crear una orden de trabajo para enviar a producción, planificando el consumo de insumos y la generación de productos finales.
**Actor principal:** Gerente
**Actor secundario:** No aplica

### Precondiciones
* El actor debe tener sesión iniciada 

### Flujo Normal
  1. El actor inicia el caso de uso presionando "Crear Orden de Trabajo"
  2. El sistema carga una plantilla para crear la orden de trabajo, solicitando:
    * Receta a seguir, indicado por una lista desplegable
    * Cantidad a producir de dicha receta
    * Fecha de inicio planificada
    * Fecha de finalización planificada    
  3. El actor ingresa los datos solicitados
  4. El sistema verifica las fechas de inicio y finalización
  5. El sistema calcula las cantidades de materia prima necesarias de cada producto que compone la receta y verifica que las existencias proyectadas en el stock sean suficientes
  6. El sistema solicita al acto una confirmación y muestra un resumen de la orden a crear.
  7. El actor presiona "Confirmar"
  8. El sistema crea la orden de trabajo, la pone en estado `EN_ESPERA` y reserva los productos necesarios del stock para llevar a cabo la orden.

### Flujos Alternativos

#### A0 Cancelación
En cualquier paso antes del paso 7
  1. **\*.1:** El actor oprime "Cancelar" y finaliza el caso de uso  

#### A1 Rango de fechas inválido
  1. **4.1:** El sistema detecta que las fecha de inicio y finalización son inválidas o incorrectas
  2. **4.2:** El sistema le solicita al usuario que ingrese correctamente las fechas y vuelve al paso 3 del flujo normal.

#### A2 Materias primas insuficientes
  1. **5.1:** El sistema detecta que no hay disponibilidad proyectada suficiente en el stock para realizar la cantidad solicitada, notificándole el máximo disponible para realizar con los materiales con los que actualmente se cuenta.
  2. **5.2:** El sistema vuelve al paso 2 del flujo normal.

### Poscondiciones
* Se genera una nueva Orden de Trabajo en la base de datos lista para ser ejecutada.
* El stock proyectado de las materias primas involucradas se actualiza, reflejando la reserva para esta nueva orden.