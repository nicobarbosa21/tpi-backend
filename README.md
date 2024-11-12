#Trabajo Práctico Integrador
#Backend de Aplicaciones 2024
#Enunciado

En este trabajo los estudiantes asumirán el rol de desarrolladores de un backend para una agencia de venta de vehículos usados que les da la posibilidad a sus clientes de realizar pruebas de manejo de sus productos.

Cada vez que un interesado quiere probar un auto, esta prueba se registra, asociando los datos del cliente, del vehículo a probar y del empleado de la agencia que acompañará al cliente.

Los vehículos que tiene para la venta la agencia cuentan con un sistema de geolocalización que transmite de forma regular la posición del mismo al backend, de manera que se puede conocer la ubicación de cada vehículo en un determinado momento.

Considerando el elevado costo de los vehículos que comercializa la agencia, instruyeron a los empleados a cargo de las pruebas que no permitan que el auto se aleje más de un cierto radio de la ubicación de la agencia. También se debe tener cuidado de que el vehículo no ingrese en ciertas zonas consideradas peligrosas por el dueño de la agencia. Las zonas se demarcan como una serie de cuadrantes definidos por dos puntos (coordenadas noroeste y sureste del cuadrante). Si algo de esto sucede (si el vehículo se aleja mucho o entra en una zona peligrosa), se le debe enviar una notificación al teléfono del empleado a cargo de la prueba indicando que haga regresar el vehículo de manera inmediata, además de agregar al cliente en una lista de clientes restringidos (no podrán probar más vehículos en la agencia).
ATENCIÓN: Para cualquier cálculo referente a las coordenadas enviadas por el vehículo, debe considerarse que se trabaja en un plano y utilizando distancia euclídea. Si algún grupo quiere trabajar con un sistema de coordenadas esféricas, no hay problema, pero no está obligado a hacerlo.

#Estructura de la base de datos
Acompaña a este trabajo práctico una base de datos con una estructura de referencia, pero que los alumnos podrán modificar si así lo desean. En esta base de referencia NO se incluye la tabla donde se almacenarán las notificaciones, es tarea de los alumnos crear esa tabla para dar soporte a los requerimientos planteados en este trabajo.

#Consignas
En base a lo explicado anteriormente, los requerimientos para el trabajo integrador son:
1. Generar los endpoints necesarios para:
a. Crear una nueva prueba, validando que el cliente no tenga la licencia vencida ni que esté restringido para probar vehículos en la agencia. Vamos a asumir que un interesado puede tener una única licencia registrada en el sistema y que todos los vehículos están patentados. También deben realizarse los controles razonables del caso; por ejemplo, que un mismo vehículo no esté siendo probado en ese mismo momento.
b. Listar todas las pruebas en curso en un momento dado
c. Finalizar una prueba, permitiéndole al empleado agregar un comentario sobre la misma.
d. Recibir la posición actual de un vehículo y evaluar si el vehículo se encuentra en una prueba para revisar si está dentro de los límites establecidos. En caso de que el vehículo se encuentre en una prueba y haya excedido el radio permitido o ingresado a una zona peligrosa, se deben disparar las acciones descriptas. ATENCIÓN: NO se espera que los alumnos hagan una notificación real a un teléfono, sino que alcanza con almacenar la notificación en la base de datos; pero si un grupo desea investigar e implementar una notificación por mail, SMS, WhatsApp o cualquier medio, tiene libertad para hacerlo.
e. Enviar una notificación de promoción a uno o más teléfonos. Aplican las mismas consideraciones que en el punto anterior.
f. Generar reportes de:
i. Incidentes (pruebas donde se excedieron los límites establecidos)
ii. Detalle de incidentes para un empleado
iii. Cantidad de kilómetros de prueba que recorrió un vehículo en un período determinado.
iv. Detalle de pruebas realizadas para un vehículo

2. Consumir un servicio externo al backend que devolverá la configuración necesaria para las funcionalidades descritas previamente. Esta configuración incluye:
a. Latitud/Longitud de la agencia
b. Radio máximo
c. Listado de zonas peligrosas.
Este servicio será provisto por la cátedra, y el trabajo de los grupos será comunicarse con él, de acuerdo a los detalles que oportunamente se brinden.

3. Implementar las medidas de seguridad que considere necesarias para que únicamente un empleado pueda crear pruebas y mandar notificaciones, únicamente un usuario asociado a un vehículo pueda enviar posiciones y solamente un administrador pueda ver los datos de los reportes.
#Restricciones obligatorias
Diseñar el backend con una arquitectura de microservicios de acuerdo a los lineamientos que se explicarán y discutirán en clases.
Para mencionar los elementos mínimos indispensables la solución debe presentar:
1. Al menos 2 microservicios que organicen los diferentes endpoints del backend solicitado de acuerdo con los lineamientos que definan sus profesores.
2. Debe implementar un Api Gateway como punto de entrada a la aplicación
3. Debe interactuar con los servicios propuestos por la cátedra
4. Debe implementar un mecanismo de seguridad para garantizar el punto 3 de las consignas.
