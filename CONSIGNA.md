# Prueba Técnica Nº 4 - Spring Boot

## Objetivo

El objetivo de este desafío es aplicar los contenidos dados hasta el momento durante el BOOTCAMP
(Git, Java, Spring Boot, Testing, JPA + Hibernate, Spring Security y JWT) en la implementación de una API REST
a partir de un enunciado propuesto, una especificación de requisitos técnico-funcionales y documentación anexada.

## Escenario

Una agencia de turismo desea llevar a cabo el desarrollo de una aplicación que le permita recibir solicitudes de
reservas para los diferentes tipos de paquetes que ofrece. Por el momento los dos servicios con los que cuenta son
el de búsqueda y reserva de hoteles y búsqueda y reserva de vuelos.

Para ello cuenta con los siguientes datos de ejemplo/referencia:

Datos Hoteles

| Código Hotel | Nombre            | Lugar/Ciudad | Tipo de Habitación | Precio por noche | Disponible Desde | Disponible hasta | Reservado |
|--------------|-------------------|--------------|--------------------|------------------|------------------|------------------|-----------|
| AR-0002      | Atlantis Resort   | Miami        | Doble              | $630             | 10/02/2024       | 20/03/2024       | NO        |
| AR-0003      | Atlantis Resort 2 | Miami        | Triple             | $820             | 10/02/2024       | 23/03/2024       | NO        |
| RC-0001      | Ritz-Carlton      | Buenos Aires | Single             | $543             | 10/02/2024       | 19/03/2024       | NO        |
| RC-0002      | Ritz-Carlton 2    | Medellín     | Doble              | $720             | 12/02/2024       | 17/04/2024       | NO        |
| GH-0002      | Grand Hyatt       | Madrid       | Doble              | $579             | 17/04/2024       | 23/05/2024       | NO        |
| GH-0001      | Grand Hyatt 2     | Buenos Aires | Single             | $415             | 02/01/2024       | 19/02/2024       | NO        |
| HL-0001      | Hilton            | Barcelona    | Single             | $390             | 23/01/2024       | 23/11/2024       | NO        |
| HL-0002      | Hilton 2          | Barcelona    | Doble              | $584             | 23/01/2024       | 15/10/2024       | NO        |
| MT-0003      | Marriott          | Barcelona    | Doble              | $702             | 15/02/2024       | 27/03/2024       | NO        |
| SH-0004      | Sheraton          | Madrid       | Múltiple           | $860             | 01/03/2024       | 17/04/2024       | NO        |
| SH-0002      | Sheraton 2        | Iguazú       | Doble              | $640             | 10/02/2024       | 20/03/2024       | NO        |
| IR-0004      | InterContinental  | Cartagena    | Múltiple           | $937             | 17/04/2024       | 12/06/2024       | NO        |

Datos Vuelos

| Nro Vuelo | Origen       | Destino      | Tipo Asiento | Precio por persona | Fecha ida  | Fecha Vuelta |
|-----------|--------------|--------------|--------------|--------------------|------------|--------------|
| BAMI-1235 | Barcelona    | Miami        | Economy      | $650               | 10/02/2024 | 15/02/2024   |
| MIMI-1420 | Miami        | Madrid       | Business     | $4320              | 10/02/2024 | 20/02/2024   |
| MIMI-1420 | Miami        | Madrid       | Economy      | $2573              | 10/02/2024 | 21/02/2024   |
| BABU-5536 | Barcelona    | Buenos Aires | Economy      | $732               | 10/02/2024 | 17/02/2024   |
| BUBA-3369 | Buenos Aires | Barcelona    | Business     | $1253              | 12/02/2024 | 23/02/2024   |
| IGBA-3369 | Iguazú       | Barcelona    | Economy      | $540               | 02/01/2024 | 16/01/2024   |
| BOCA-4213 | Bogotá       | Cartagena    | Economy      | $800               | 23/01/2024 | 05/02/2024   |
| CAME-0321 | Cartagena    | Medellín     | Economy      | $780               | 23/01/2024 | 31/01/2024   |
| BOIG-6567 | Bogotá       | Iguazú       | Business     | $570               | 15/02/2024 | 28/02/2024   |
| BOBA-6567 | Bogotá       | Buenos Aires | Economy      | $398               | 01/03/2024 | 14/03/2024   |
| BOME-4442 | Bogotá       | Madrid       | Economy      | $1100              | 10/02/2024 | 24/02/2024   |
| MEMI-9986 | Medellín     | Miami        | Business     | $1164              | 17/04/2024 | 02/05/2024   |

📝 A partir de esta información brindada por la agencia, un analista funcional llevó a cabo el relevamiento de los
requerimientos técnicos funcionales y ha proporcionado una documentación técnico-funcional que se detalla en un
documento aparte llamado: 2. Requerimientos Funcionales

📝 Conociendo estos requerimientos se solicita desarrollar la API correspondiente para lograr la consulta y reserva tanto
de hoteles como de vuelos con los que trabaja la agencia.

## Condiciones de Entrega

- Código fuente de la aplicación en un repositorio de control de versiones (GitHub).

       - Utilizar como formato de nombre del repositorio la siguiente combinación: apellido+nombre+_pruebatec4. Ej: dePaulaLuisina_pruebatec4

- Un informe o documentación breve que describa cómo ejecutar y probar la aplicación. En caso de que algunos
  requerimientos no estén claros en la presente documentación, se permite el uso de SUPUESTOS. Esto puede ser incluido
  en el archivo README de GitHub.

- Fecha de entrega máxima Viernes 05/01/2023 a las 23:59 hs

- Base de datos de referencia en formato .sql

# Requerimientos Funcionales

## Historias de usuario de Hoteles

User Story Nº 1:  Obtener un listado de todos los hoteles registrados

- *Método GET*

- *Path: /agency/hotels*

User Story Nº 2:  Obtener un listado de todos los hoteles disponibles en un determinado rango de fechas y según el
destino seleccionado.

- *Método GET*

- *Path: /agency/hotels?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&destination="nombre_destino"*

User Story Nº 3: Realizar una reserva de un hotel, indicando cantidad de personas, fecha de entrada, fecha de salida y
tipo de habitación. Obtener como respuesta el monto total de la reserva realizada

- *Método POST*

- *Path: /agency/hotel-booking/new*

Ejemplo de JSON request (de referencia)

``` 
{
"dateFrom" : "05/04/2024",
"dateTo" : "09/04/2024",
"nights" : 4
"place" : "Barcelona",
"hotelCode" : "MT-0003",
"peopleQ" : 2,
"roomType" : "Double",
"hosts" : [
{datos persona 1},
{datos persona 2}
]
}
``` 

⚠️ Se pueden realizar cambios en el JSON en caso de ser necesario, el mismo es solo de referencia.

⚠️ Para ninguna de estas historias de usuario es necesario estar autenticado para acceder a los endpoints ya que son
requerimientos que utilizarán los propios clientes de la empresa.

## Historias de usuario de Vuelos

User Story Nº 4:  Obtener un listado de todos los vuelos registrados

- *Método GET*

- *Path: /agency/flights*

User Story Nº 5: Obtener un listado de todos los vuelos disponibles en un determinado rango de fechas y según el destino
y el origen seleccionados.

- *Método GET*

- *Path: /agency/flights?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&origin="ciudadOrigen"&destination="ciudadDestino"*

User Story Nº 6:  Realizar una reserva de un vuelo, indicando cantidad de personas, origen, destino y fecha de ida.
Obtener como respuesta el monto total de la reserva realizada.

- *Método GET*

- *Path: /agency/flight-booking/new*

⚠️Recordar que solo se especifica fecha de ida, en caso de que sea ida y vuelta deben ser dos vuelos separados y se
invierte el orden de origen y destino. Esto lo hace el usuario que compra. No es necesario llevarlo a cabo mediante
código

Ejemplo de JSON request (de referencia)

``` 
{
"date" : "12/04/2024",
"Origin" : "Buenos Aires"
"destination" : "Barcelona",
"flightCode" : "BUBA-3369",
"peopleQ" : 2,
"seatType" : "Economy",
"passengers" : [
    {datos persona 1},
    {datos persona 2}
]
}

``` 

⚠️ Se pueden realizar cambios en el JSON en caso de ser necesario, el mismo es solo de referencia.

⚠️ Para ninguna de estas historias de usuario es necesario estar autenticado para acceder a los endpoints ya que son
requerimientos que utilizarán los propios clientes de la empresa.

Ejemplos para requests:

*Hoteles*

```
{
"hotelCode": “String”,
"name": “String”,
"place": “String”,
"roomType": “String”,
"roomPrice": Double,
"disponibilityDateFrom": “dd/mm/yyyy”,
"disponibilityDateTo": “dd/mm/yyyy”,
"isBooked": “boolean”
}
```

Vuelos

```
{
"flightNumber": “String”,
"name": “String”,
"origin": “String”,
"destination": “String”,
"seatType": “String”,
"flightPrice": Double,
"date": “dd/mm/yyyy”,
}

```

## Validaciones necesarias (básicas)

- Para bajas y modificaciones debe existir el hotel, reserva o vuelo correspondiente. Caso contrario, se debe retornar
  el correspondiente status code y msje.

- Para las consultas, en caso de no encontrar resultados se debe informar dicha situación mediante un mensaje.

- Para altas, validar que no exista anteriormente una reserva con idénticas características.

- Antes de dar de baja un vuelo o un hotel, validar que no se encuentre actualmente en una reserva. En caso de que sea
  así, no se podrá eliminar el registro, sin antes haber cancelado/eliminado la reserva.

Cualquier validación extra necesaria o complementaria que se considere necesaria puede ser implementada sin problema
alguno.

## Extra (sugerencias)

✅ A continuación se sugiere una serie de test unitarios a llevar a cabo; sin embargo, en caso de que se considere
necesario implementar otros, esto es totalmente viable.

✅Se sugiere al menos la implementación de AL MENOS 1 TEST UNITARIO para manifestar la correcta comprensión de uso de
ésta herramienta.

⚠️ Nota: Tener en cuenta que los datos de entrada pueden variar dependiendo del modelado que haya sido realizado por
cada desarrollador. En caso de corresponder, realizar las modificaciones/adaptaciones correspondientes necesarias en los
tests unitarios sugeridos.

| User Story | Situaciones/Datos de entrada                                      | Comportamiento Esperado                                                                                                                                                                                          |
|------------|-------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1          | Se envía solicitud de listado de todos los hoteles registrados.   | - Si hay hoteles registrados: Permite continuar con normalidad y muestra listado completo. - Si no hay hoteles: Notifica la no existencia mediante una excepción.                                                |
| 2          | Se envía solicitud de listado de todos los hoteles disponibles... | - Si hay registros que cumplan el criterio: Se debe obtener un listado de los hoteles disponibles en ese rango de fechas en esos destinos. - No se cumple: Notifica la situación mediante una excepción.         |
| 3          | Se envía solicitud de reserva de un hotel...                      | - Se cumplen todos los criterios: Responde un Status code 200 con el monto total de la reserva. Da de alta una nueva reserva. - No se cumple: Notifica error/imposibilidad de finalizar la transacción.          |
| 4          | Se envía solicitud de listado de todos los vuelos registrados.    | - Si hay vuelos registrados: Permite continuar con normalidad y muestra listado completo. - Si no hay vuelos registrados: Notifica la no existencia mediante una excepción.                                      |
| 5          | Se envía solicitud de listado de todos los vuelos disponibles...  | - Si hay registros que cumplan el criterio: Se debe obtener un listado de los vuelos disponibles en ese rango de fechas en esos destinos. - No se cumple: Notifica la situación mediante una excepción.          |
| 6          | Se envía solicitud de reserva de un vuelo...                      | - Se cumplen todos los criterios: Responde un Status code 200 con el monto total de la reserva. Da de alta una nueva reserva de vuelo. - No se cumple: Notifica error/imposibilidad de finalizar la transacción. |
