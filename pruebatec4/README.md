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
    spring.datasource.url=jdbc:mysql://localhost:3306/agencia 
    spring.datasource.username=usuario_mysql
    spring.datasource.password=contraseña_mysql
    spring.jpa.hibernate.ddl-auto=update
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

-
    7. **Acceder a la documentación de la API con Swagger:**

    - Una vez que la aplicación esté en funcionamiento, accede a la documentación de la API a través de Swagger.
    - Abre un navegador web y ve a la siguiente URL: `http://localhost:puerto/swagger-ui.html`
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

## Pruebas ⚙️

#### Resultado obtenido:

## Versionado 📌

Versión: 1.0.0

## Autores ✒️

* **Alicia Martínez Maqueda** *https://github.com/aliciamm81*