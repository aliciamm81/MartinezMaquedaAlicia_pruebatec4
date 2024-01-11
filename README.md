# Desarrollo de una Aplicación de Gestión de una Agencia de Viajes

**Martinez Maqueda Alicia - pruebatec4**

## Comenzando 🚀

Con estas instrucciones podrás obtener
una copia del proyecto en tu repositorio local y también conocerás el funcionamiento de la aplicación.

## Pre-requisitos 📋

- [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) instalado (versión
  11 o superior).
- [Maven](https://maven.apache.org/download.cgi) instalado.
- [MySQL](https://dev.mysql.com/downloads/) instalado localmente o disponible como servicio en la nube.
- [PHPMyAdmin](https://www.phpmyadmin.net/downloads/) instalado o accedido a través de un servicio en la nube.

## Despliegue de la Aplicación con Spring Boot y MySQL con PHPMyAdmin 📦

### Pasos para la configuración

1. **Clonar el repositorio:**

    ```bash
    git clone https://github.com/tu-usuario/tu-proyecto.git
    ```

2. **Configurar la Base de Datos:**

    - Accede a PHPMyAdmin y crea una base de datos llamada `agencia`.

3. **Configurar la Aplicación:**

    - Abre el archivo `application.properties` en `src/main/resources` y actualiza la configuración de la base de datos
      con tus credenciales:

    ```properties
    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.url=jdbc:mysql://localhost:3306/agencia?useSSL=false&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=
    spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
    spring.security.user.name=admin
    spring.security.user.password=1234
    #habilitar/desahabilitar api-docs - swagger-ui
    springdoc.api-docs.enabled=true
    springdoc.swagger-ui.enabled=true
    #definir la ruta de swagger-ui
    springdoc.swagger-ui.path=/doc/swagger-ui.html


    ```

4. **Compilar y ejecutar la aplicación:**

    - Utiliza Maven para compilar y ejecutar la aplicación Spring Boot:

    ```bash
    mvn spring-boot:run
    ```

5. **Acceder a la Aplicación:**

    - La aplicación estará disponible en `http://localhost:puerto`.

6. **Comprobar la Aplicación:**

    - Accede a la URL de la aplicación en el navegador o mediante herramientas como Postman para probar los endpoints
      definidos.

7. **Acceder a la documentación de la API con Swagger:**

    - Una vez que la aplicación esté en funcionamiento, accede a la documentación de la API a través de Swagger.
    - Abre un navegador web y ve a la siguiente URL: `http://localhost:8080/doc/swagger-ui/index.html?continuel`
    - Esto te llevará a la interfaz de Swagger, donde podrás ver y probar todos los endpoints disponibles en la
      aplicación.

8. **Explorar y probar los endpoints:**

    - En la interfaz de Swagger, explora los diferentes endpoints documentados y utiliza las opciones proporcionadas
      para probar cada uno de ellos.
    - Puedes enviar solicitudes a los endpoints directamente desde la interfaz de Swagger y ver las respuestas
      correspondientes.

9. **Interactuar con la API:**

    - Utiliza los botones "Try it out" para probar cada endpoint y enviar diferentes tipos de solicitudes (GET, POST,
      PUT, DELETE, etc.).
    - Observa las respuestas y los códigos de estado devueltos para validar el comportamiento de la API.

## Instrucciones de la aplicación 📄

La aplicación proporciona los siguientes endpoints:

    Vuelos:
        PUT /agencia/vuelos/actualizar/{id} - Actualizar vuelo por ID
        POST /agencia/vuelos/nuevo - Crear un vuelo nuevo
        GET /agencia/vuelos - Obtener todos los vuelos
        GET /agencia/vuelos/{id} - Buscar vuelo por su ID
        GET /agencia/vuelos/buscar - Buscar vuelos disponibles
        DELETE /agencia/vuelos/eliminar/{id} - Eliminar vuelo por ID

    Reservas de Vuelo:
        PUT /agencia/reserva-vuelo/actualizar/{id} - Actualizar reserva de vuelo por ID
        POST /agencia/reserva-vuelo/nuevo - Crear nueva reserva de vuelo
        GET /agencia/reserva-vuelo - Obtener todas las reservas de vuelo
        GET /agencia/reserva-vuelo/{id} - Obtener reserva de vuelo por ID
        DELETE /agencia/reserva-vuelo/eliminar/{id} - Eliminar reserva de vuelo por ID
      
    Hoteles: 
        PUT /agencia/hoteles/actualizar/{id} - Actualizar un hotel por ID
        POST /agencia/hoteles/nuevo - Crear un hotel nuevo
        GET /agencia/hoteles - Obtener todos los hoteles
        GET /agencia/hoteles/{id} - Obtener un hotel por su ID
        GET /agencia/hoteles/buscar - Obtener hoteles disponibles por fechas y destino
        DELETE /agencia/hoteles/eliminar/{id} - Eliminar un hotel por ID

    Reservas de Hotel:
        PUT /agencia/reserva-hotel/actualizar/{id} - Actualizar reserva de hotel por ID de reserva
        POST /agencia/reserva-hotel/nuevo - Crear una reserva de hotel
        GET /agencia/reserva-hotel - Obtener reservas de todos los hoteles
        GET /agencia/reserva-hotel/{id} - Obtener reserva de hoteles por ID de reservas
        GET /agencia/reserva-hotel/hotel/{id} - Obtener reservas por ID de hotel
        DELETE /agencia/reserva-hotel/eliminar/{id} - Eliminar reserva de Hotel por ID

    Habitaciones:
        PUT /agencia/hoteles/habitaciones/actualizar/{id} - Actualizar habitación por ID
        POST /agencia/hoteles/habitaciones/nuevo - Crear una habitación nueva
        GET /agencia/hoteles/habitaciones - Obtener todas las habitaciones
        GET /agencia/hoteles/habitaciones/{id} - Obtener habitación por ID
        GET /agencia/hoteles/habitaciones/hotel/{id} - Obtener habitaciones por ID de hotel
        DELETE /agencia/hoteles/habitaciones/eliminar/{id} - Eliminar habitación por ID

### Detalles a tener en cuenta

#### Crear Hotel y Habitaciones

Antes de crear habitaciones, asegúrate de que el hotel correspondiente ya esté creado. Sigue estos pasos:

    - Crear Hotel:
        Utiliza el endpoint POST /agencia/hoteles/nuevo para crear un nuevo hotel.
        Proporciona los detalles del hotel, como nombre, dirección y otros requisitos.

    - Crear Habitaciones:
        Una vez creado el hotel, puedes asignar habitaciones a ese hotel.
        Los códigos de habitación deben coincidir exactamente con el del hotel, pero con un guion y cuatro dígitos adicionales.
        Ejemplo: Código del Hotel: AR-0003, Código de Habitación: AR-0003-0001.

#### Listar Hoteles y Reservas

Antes de listar un hotel o realizar una reserva, asegúrate de tener habitaciones creadas y que no estén dadas de baja.
Ten en cuenta lo siguiente:

    - Borrado Lógico:
        La aplicación utiliza un borrado lógico, lo que significa que los datos no se eliminan por completo. Simplemente, no se mostrarán.
        Esto garantiza un historial de datos completo y la posibilidad de recuperar información relevante.
    - Eliminación de Hoteles:

        No se pueden eliminar hoteles que tengan reservas pendientes. Asegúrate de cancelar todas las reservas asociadas antes de intentar eliminar un hotel.

Ahora, puedes utilizar los siguientes endpoints para listar hoteles y realizar reservas:

    - Listar Hoteles:
        Endpoint: GET /agencia/hoteles
        Obtiene la lista de todos los hoteles registrados.

    - Realizar Reserva:
        Endpoint: POST /agencia/reserva-hotel/nuevo (para reservas de hoteles) o POST /agencia/reserva-vuelo/nuevo (para reservas de vuelos).
        Crea una nueva reserva proporcionando la información necesaria.
        Asegúrate de conocer el código de la habitación antes de realizar la reserva.

#### Crear Vuelos

Para crear vuelos, sigue estos pasos:

    - Código del Vuelo:
        Utiliza el endpoint POST /agencia/vuelos/nuevo para crear un nuevo vuelo.
        El código del vuelo se compone de las dos primeras letras del origen y las dos primeras letras del destino, seguidas de un guion y cuatro dígitos. Ejemplo: MADR-0233 para un vuelo de Madrid a Valencia.

    - Evitar Repetición de Códigos:
        Se permite que los códigos de vuelo se repitan en diferentes días.
        No puede existir un vuelo con el mismo código, fecha, origen y destino. La combinación de estos elementos debe ser única.

    - No Borrar Vuelos con Reservas:
        No es posible borrar un vuelo que tenga reservas asociadas. El borrado es lógico, asegurando que los datos permanezcan en la base de datos, pero no se mostrarán.

    - Asientos Disponibles:
        Cada vez que se crea, actualiza o borra una reserva, los asientos disponibles se ajustarán según el tipo de vuelo.

#### Listar Vuelos

Utiliza el siguiente endpoint para listar vuelos:

    - Listar Vuelos:
        Endpoint: GET /agencia/vuelos
        Obtiene la lista de todos los vuelos registrados.

Estas instrucciones cubren las funciones básicas relacionadas con la gestión de vuelos.
Además, ten en cuenta que la aplicación mantiene una base de datos para los datos de clientes, creando nuevos registros
si es necesario al asociarlos a reservas.

Ten en cuenta que en todas las tablas de la aplicación, se registran los siguientes datos para controlar la gestión de
registros:

    - Campos de Usuario Alta:
        Usuario responsable del registro.
        Fecha de creación del registro.

    - Campos de Usuario Baja:
        Usuario responsable de la baja lógica.
        Fecha de baja lógica del registro

Estas instrucciones te ayudarán a utilizar las funciones básicas de la aplicación.

#### Seguridad

Las siguientes rutas están abiertas para acceso público, de acuerdo con los requisitos:

    /agencia/vuelos: Permite acceder a la información de vuelos disponibles.
    /agencia/vuelos/buscar: Posibilita la búsqueda de vuelos según diversos criterios.
    /agencia/reserva-vuelo/nuevo: Permite realizar nuevas reservas de vuelos.
    /agencia/hoteles: Da acceso a la información de hoteles disponibles.
    /agencia/hoteles/buscar: Facilita la búsqueda de hoteles según diferentes parámetros.
    /agencia/hoteles/habitaciones: Proporciona detalles sobre las habitaciones disponibles en los hoteles.
    /agencia/reserva-hotel/nuevo: Permite realizar nuevas reservas de habitaciones de hotel.

Se destaca que otras rutas han sido protegidas con clave, reconociendo su sensibilidad en términos de protección de
datos

## Pruebas ⚙️

### Prueba de Hoteles:

*Crear un Hotel nuevo:*

Se introduce el siguiente Json en postman a través de la ruta localhost:8080/agencia/hoteles/nuevo

```
{
"codigo": "AR-0002",
"nombre": "Atlantis Resort",
"ciudad": "Miami"
}
```

#### Resultado obtenido:

```
Hotel creado correctamente
```

*Crear habitación de hotel:*

Se introduce el siguiente JSON en postman a través de la ruta localhost:8080/agencia/hoteles/habitaciones/nuevo

```
{
"codHotel": "AR-0002",
"codigo": "AR-0002-0001",
"tipo": "doble",
"descripcion": "habitacion con baño privado y vistas al mar",
"precio": 630.0
}
```

#### Resultado obtenido:

```
Habitacion creada correctamente
```

*Listar hoteles:*

Se solicita por la siguiente ruta un listado de todos los hoteles localhost:8080/agencia/hoteles

#### Resultado obtenido:

```
[
    {
    "id": 1,
    "codigo": "AR-0002",
    "nombre": "Atlantis Resort",
    "ciudad": "Miami",
    "usuarioAlta": "admin",
    "fechaAlta": "11-01-2024",
    "usuarioBaja": null,
    "fechaBaja": null
    }
]
```

### Prueba de Reservas de Hoteles:

*Crear una reserva*

Se solicita por la siguiente ruta localhost:8080/agencia/reserva-hotel/nuevo la creación de una reserva con el siguiente
JSON :

```
{
"fechaDesde": "01-09-2024",
"fechaHasta": "03-09-2024",
"numPersonas": 2,
"nombre": "Juan",
"apellido": "Lopez",
"dni": "12346986A",
"email": "juan@example.com",
"telefono": 677154523,
"numTarjeta": 156454444423456,
"codigoHabitacion": "AR-0002-0001"
}
```

#### Resultado obtenido:

```
Reserva creada correctamente, precio total: 1260.0
```

*Obtener hoteles disponibles para rango de fechas y ciudad:*

Se introduce la siguiente ruta con unos parámetros iguales a la reserva anterior realizada

```
localhost:8080/agencia/hoteles/buscar?fechaDesde=01-09-2024&fechaHasta=03-09-2024&destino=Miami
```

#### Resultado obtenido:

```
No hay hoteles que cumplan estos criterios en la base de datos
```

Se introduce otra ruta con unos parámetros con fechas diferentes a la reserva realizada:

#### Resultado obtenido:

```
[
    {
        "id": 1,
        "codigo": "AR-0002",
        "nombre": "Atlantis Resort",
        "ciudad": "Miami",
        "usuarioAlta": "admin",
        "fechaAlta": "11-01-2024",
        "usuarioBaja": null,
        "fechaBaja": null
    }
]
```

### Prueba de Vuelos:

*Crear vuelos*

Se crea un vuelo con el siguiente JSON en la ruta: localhost:8080/agencia/vuelos/nuevo

```
{
"numVuelo" : "BAMI-1235",
"origen": "Barcelona",
"destino": "Miami",
"fecha": "10-02-2024",
"asientosEconomicos": 50,
"precioAsientoEconomico": 50.0,
"asientosPremium": 50,
"precioAsientoPremium": 200.0
}
```

#### Resultado obtenido:

```
Vuelo creado correctamente
```

*Listar vuelos*

Se listan todos los vuelos accediendo a la ruta:

```
localhost:8080/agencia/vuelos
```

#### Resultado obtenido:

```
[
{
"id": 1,
"numVuelo": "BAMI-1235",
"origen": "Barcelona",
"destino": "Miami",
"fecha": "10-02-2024",
"asientosEconomicos": 50,
"precioAsientoEconomico": 50.0,
"asientosPremium": 50,
"precioAsientoPremium": 200.0,
"asientosEconomicosDisponibles": 50,
"asientosPremiumDisponibles": 50,
"usuarioAlta": "admin",
"fechaAlta": "11-01-2024",
"usuarioBaja": null,
"fechaBaja": null
}
]
```

### Prueba de Reserva de Vuelos:

*Crear una reserva de vuelo:*

Se introduce el siguiente JSON en la ruta localhost:8080/agencia/reserva-vuelo/nuevo
```
{
"numPersonas": 5,
"tipoAsiento": "economico",
"fecha": "10-02-2024",
"origen": "Barcelona",
"destino": "Miami",
"nombre": "Juan",
"apellido": "Pérez",
"dni": "12345699A",
"email": "juan@example.com",
"telefono": 123455456789,
"numTarjeta": 1234554564456789
}
```
#### Resultado obtenido:

```
Reserva creada correctamente, precio total: 250.0
```

*Obtener vuelos por fecha y origen-destino*

Se introduce la siguiente ruta para comprobar los vuelos disponibles:

```
localhost:8080/agencia/vuelos/buscar?fechaDesde=01-02-2024&fechaHasta=31-02-2024&origen=Barcelona&destino=Miami
```

#### Resultado obtenido:

```
[
    {
    "id": 1,
    "numVuelo": "BAMI-1235",
    "origen": "Barcelona",
    "destino": "Miami",
    "fecha": "10-02-2024",
    "asientosEconomicos": 50,
    "precioAsientoEconomico": 50.0,
    "asientosPremium": 50,
    "precioAsientoPremium": 200.0,
    "asientosEconomicosDisponibles": 45,
    "asientosPremiumDisponibles": 50,
    "usuarioAlta": "admin",
    "fechaAlta": "11-01-2024",
    "usuarioBaja": null,
    "fechaBaja": null
    }
]
```

## Mejora Prevista para los Siguientes Sprints 🔧

En la próxima iteración, planeo implementar las siguientes mejoras en la aplicación:

### Refactorización de Código

Focalizaré mis esfuerzos en la refactorización del código, investigando sobre los mappers o adapters de Spring Boot. El
objetivo es optimizar la transformación de DTOs en modelos o entidades de manera más eficiente.

### Generación Automática de Códigos

Buscaré implementar la generación automática de códigos para hoteles, vuelos y habitaciones. Esto eliminará la necesidad
de ingresar estos códigos manualmente, mejorando la eficiencia del sistema.

### Consistencia en Mensajes de Controller

Revisaré y ajustaré los mensajes devueltos por cada controlador para que sigan un patrón consistente y sugerente. Aunque
no fue posible abordar este aspecto en el sprint actual, es una prioridad para la próxima iteración.

### Creación Rápida de Hoteles y Habitaciones

Facilitaré la creación de habitaciones al mismo tiempo que se crea un hotel. Simplificaré el proceso para agilizar la
configuración inicial del sistema. Implementaré un enfoque que posibilite la reserva de hoteles disponibles mediante
la información de fecha de entrada, fecha de salida, ciudad y disponibilidad, en lugar de depender exclusivamente del
código de habitación, como se hace actualmente.

### Registro de Usuarios

Crearé una tabla de usuarios registrados para tener un mejor control sobre quién crea y elimina reservas. Actualmente,
solo hay un usuario general (admin), y esta mejora proporcionará una trazabilidad más detallada de las acciones.

### Entidad de Ocupación de Hoteles

Introduciré una tabla o entidad separada para gestionar la ocupación de los hoteles. Esto mejorará el control de la
disponibilidad de cada hotel y facilitará la gestión de reservas.

### Optimización de Métodos en Servicios

Revisaré los métodos en los servicios para identificar posibles redundancias y optimizaciones. Buscaré prescindir de
métodos innecesarios y aprovechar funciones ya existentes para tareas como la actualización de asientos o el cálculo de
precios.

### Entidad de Precios por Temporadas

Implementaré una entidad separada para gestionar los precios por temporadas (baja, alta y media). Esto permitirá aplicar
tarifas diferenciadas según la temporada, mejorando la flexibilidad en la gestión de precios.

### Manejo Optimizado de Excepciones

Revisaré y mejoraré el manejo de excepciones en la aplicación para garantizar un enfoque más óptimo y consistente.

### Implementación de DTO en respuestas

Estoy trabajando en una mejora que implica la implementación de un DTO que se utilizará para filtrar datos innecesarios
en las respuestas, mejorando la eficiencia y la claridad de la información proporcionada. De esta forma no se mostrarán
como pasa ahora los datos de usuarioBaja o fechaBaja que son innecesarios.

### Docker

Se tiene previsto crear un archivo Docker posteriormente para facilitar el despliegue de la aplicación. Actualmente,
se enfrentan dificultades con la conexión entre la aplicación, MySQL y phpMyAdmin en Docker, las cuales se abordarán
y resolverán para asegurar un despliegue exitoso.

### Ajustes en Swagger 

Se realizará unos ajustes en la documentación de Swagger para que los casos de ejemplo que muestra seán ejemplos reales
y fáciles de entender, para ello se utilizará Schemas tal como se indica en la documentación y tengo estudiar para ver 
como se aplica. https://swagger.io/docs/specification/data-models/

## Versionado 📌

Versión: 1.0.0

## Autores ✒️

* **Alicia Martínez Maqueda** *https://github.com/aliciamm81*