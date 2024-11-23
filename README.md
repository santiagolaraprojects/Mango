# Solución para la Prueba Técnica

Este proyecto es mi solución a la prueba técnica asignada. Se ha desarrollado utilizando una **arquitectura hexagonal** combinada con el enfoque de **vertical slicing**. Estas elecciones de diseño responden a las necesidades de mantener un código limpio, flexible y fácil de mantener, priorizando la separación de responsabilidades y facilitando el testeo y la escalabilidad.

## Motivación de la Arquitectura Elegida

La arquitectura hexagonal se eligió por sus beneficios clave:
- **Alta testabilidad**: Separar las dependencias externas facilita la creación de tests unitarios y de integración.
- **Facilidad de mantenimiento**: Un diseño claro y modular hace que las futuras modificaciones sean más simples y seguras.

El enfoque de **vertical slicing** permite que las funcionalidades del sistema estén organizadas por casos de uso facilitando la navegación por el código.

## Base de Datos y Adaptadores

Dado que el enunciado no especificaba un tipo de base de datos, el adaptador correspondiente no tiene funciones implementadas. Sin embargo:
- Se ha incluido una implementación comentada para el uso de una base de datos como **PostgreSQL**, con configuraciones necesarias para que los repositorios puedan interactuar con la base de datos utilizando **Spring Data JPA**.
- Este enfoque asegura que el revisor pueda comprobar fácilmente el build y ejecutar el servidor y los tests sin necesidad de configurar una base de datos en local.

### Consideraciones sobre las Entidades

Las entidades y adaptadores tienen también una implementación comentada. No obstante, faltarían:
- **Validaciones específicas para cada base de datos**: Como restricciones o índices (Ya que estas dependen del tipo de base de datos (SQL o NOSQL) como por ejemplo el uso de tablas o documentos en la anotación.
- Las anotaciones correspondientes en las entidades para adaptarlas al modelo de datos requerido.

## Ejecución y Build del Proyecto

El proyecto puede ser ejecutado sin necesidad de configurar una base de datos, asegurando un proceso de revisión sencillo. Para usar la implementación comentada con una base de datos real:
1. Inlcuir en properties las credenciales a la base de datos local.
2. Descomenta las implementaciones en los repositorios y las entidades.


## Testing

El proyecto incluye tests unitarios de todas las clases que contienen una mínima parte de lógica para garantizar la funcionalidad de los casos de uso implementados. La arquitectura facilita la creación de tests adicionales según sea necesario.

---

Gracias por la oportunidad de trabajar en esta prueba. Si tiene preguntas o necesita clarificaciones sobre alguna parte del código, no dude en contactarme.
