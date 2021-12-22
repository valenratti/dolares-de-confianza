# Dólares de Confianza
Bases de Datos II - Diciembre 2022

## Autores

- **Lautaro Galende** <lgalende@itba.edu.ar>
- **Sebastián Itokazu** <sitokazu@itba.edu.ar>
- **Valentín Ratti** <vratti@itba.edu.ar>

## Instrucciones
Posicionado en la carpeta raíz ejecutar:
```shell
docker-compose up
```
Este comando creará dos bases de datos: postgres y neo4j.

Para neo4j, habrá una interfaz disponible en http://localhost:7474/

Para postgres, se podrá acceder mediante pgadmin, datagrip o similar.

Además, se disponibilizará una API en http://localhost:8080/swagger-ui.html#/

Otra forma de utilizar la aplicación es ejecutando postgres (con postgis) y neo4j de manera local. Y ejecutar la app desde el IDE.

### ¿Cómo usar la API?

#### User controller (/api/users)
**/sign-up** -> para crear un nuevo usuario

**/authenticate** -> autenticarse con las credenciales creadas en sign-up y utilizar el token para obtener autorización

**/explore** -> para buscar usuarios por cercania

**/{userId}/invite** -> para enviar una solicitud de amistad a un usuario

**/invite/{inviteId}** -> para aceptar/declinar una solicitud recibida

#### Offer controller (/api/offers)
**/** -> para crear una oferta

**/explore** -> para buscar ofertas (filtrando por rate, amount, distance, trustLevel)

**/{offerId}** -> para eliminar una oferta creada

##### Aclaraciones
**1)** Trust level: cantidad de saltos en el grafo. Por ejemplo:

- amigos -> trust level = 1
- amigos de amigos -> trust level = 2

**2)** Al crear una oferta:
- fromCurrency: es la moneda que tiene para vender el creador de la oferta
- toCurrency: es la moneda que debe tener el que explora las ofertas
- fromToRate: fromToRate * toCurrency = 1 * fromCurrency
- minAmount y maxAmount estan medidos en la moneda fromCurrency

**Ejemplo 1:**

From currency: USD
To currency: ARS
FromToRate: 200.0

**Ejemplo 2:**

From currency: ARS
To currency: USD
FromToRate: 0.05

Si bien ahora es un tanto confuso, cuando tenga un frontend será transparente para el usuario.