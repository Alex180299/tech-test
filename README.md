# Technical test

API Rest construida con Spring boot y JPA para consultar el clima de ciudades a partir de su nombre.

## Estructura del proyecto


    .
    ├── configuration               # Contiene la configuración de inyección de dependencias con spring
    ├── domain                      # Contiene los modelos de negocio (Ciudad)
    ├── infrastructure              # Contiene las capas de infraestructura (controlador, repository y cliente rest)
    ├── use_case                    # Contiene los casos de uso del negocio contenidos en servicios
    └── TestApplication.kt		    # Punto de entrada a la aplicación 

## Ejecución del proyecto

El proyecto puede ser ejecutado localmente ejecutando la función **main** dentro del archivo **TestApplication.kt**

## Ejecución con Docker

Adicionalmente puede ser ejecutado con docker **(se requiere tener instalado docker)** por medio del **Dockerfile** con las siguientes instrucciones ejecutadas en el directorio raíz:

### Compilar aplicación

**Linux:**

    ./gradlew build

**Windows:**

    gradlew.bat build

### Construir la imagen

    docker build -t tech-test .

### Correr el contenedor en local haciendo binding del puerto

    docker run -d -p 8080:8080 tech-test -e DB_HOST=localhost -e DB_PORT=5432 -e DB_NAME=api -e DB_USER=user -e DB_PASSWORD=password

El problema de esta ejecución es que se debe tener montada la base de datos localmente para que la aplicación la utilice, por lo que se agrega una solución adicional.

## Ejecución con Docker Compose

### Compilar aplicación

**Linux:**

    ./gradlew build

**Windows:**

    gradlew.bat build

### Levantar contenedores

    docker-compose build --no-cache

    docker-compose up -d

## Consumo de api rest

Para el consumo de la api rest se ha proporcionado una colección de postman dentro del directorio assets, la cual se puede importar de manera local

		
