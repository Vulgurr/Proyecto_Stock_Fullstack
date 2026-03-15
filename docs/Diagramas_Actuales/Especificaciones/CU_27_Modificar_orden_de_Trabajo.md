# Modificar Orden de Trabajo

**ID del CU:** CU_27
**Tipo de CU:** Gestión
**Objetivo del CU:** Permite modificar los valores de una orden de trabajo.
**Actor principal:** Administrativo
**Actor secundario:** No aplica

### Precondiciones
* El actor debe tener sesión iniciada 
* Debe existir al menos una orden de trabajo en el sistema que no esté en estado `FINALIZADA`, `ABORTADA` o `RECHAZADA`.

### Reglas de Negocio
* **Modificaciones permitidas:** Un administrativo solo puede modificar los datos de consumo reales o las fechas de las órdenes de trabajo. Un gerente puede modificar cualquier órden y los campos que desee. En ningún caso se pueden modificar órdenes con estado `FINALIZADA`, `ABORTADA` o `RECHAZADA`.

### Flujo Normal
  1. El actor inicia el caso de uso oprimiendo "Modificar" en una Orden de Trabajo.
  2. El sistema verifica los permisos del actor para realizar la modificación.
  3. El sistema muestra los campos de la Orden de Trabajo a modificar.
  4. Para cada campo que el actor quiera modificar:
    1. **4.1:** El actor modifica el campo de acuerdo a las Reglas del Negocio.
  5. El actor oprime "Confirmar"
  6. El sistema verifica los nuevos datos de la orden de trabajo, actualiza los datos de la orden en la base de datos y recalcula la disponibilidad de stock real si se modificaron las cantidades reales.
  7. El sistema muestra un mensaje de éxito.

### Flujos Alternativos

#### A0 Cancelación
En cualquier paso antes del paso 5
  1. **\*.1:** El actor oprime "Cancelar" y finaliza el caso de uso  

#### A1 Orden inválida
  1. **6.1:** El sistema detecta que la orden de trabajo modificada es imposible de realizar.
  2. **6.2:** El sistema muestra un mensaje de error y finaliza el caso de uso.

#### A2 Permisos insuficientes
  1. **2.1:** El sistema detecta que el actor no cuenta con los permisos necesarios para realizar la modificación
  2. **2.2:** El sistema muestra un mensaje de "Permisos insuficientes" y finaliza el caso de uso.


### Poscondiciones
* Las modificaciones necesarias se realizaron en la orden de trabajo.
* Las modificaciones impactaron en las bases de datos y el stock proyectado o real.