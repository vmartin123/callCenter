# Call Center

Este ejercicio consiste en un call center donde 3 tipos de empleados trabajan: *OPERADOR*, *SUPERVISOR* y *DIRECTOR*

El objetivo principal es gestionar las llamadas entrantes y asignarlas a los empleados a traves del *Dispatcher*

## Solucion

La solucion principal es utilizar hilos donde los empleados pueden funcionar (atender llamadas, que son solo invocaciones a los metodos *sleep*)

Cuando no haya mas empleados libre, las llamadas se colocaran en una cola concurrente y esperan hasta que algun empleado este disponible
(Mas info en la clase *Dispatcher*, linea 31)

Para cuando se tienen mas de diez llamadas, se validara que mientras exceda el maximo no se les asignara empleados (aun cuando esten disponibles) y se dejaran en la cola
hasta que haya menos de 10 llamadas activas
(Mas info en la clase *EmployeeService*, linea 20)

## Autor

Victor Martin
