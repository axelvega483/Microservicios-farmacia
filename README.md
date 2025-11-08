<h1 align="center">
  💊 Sistema de Gestión para Farmacia (Microservicios)
</h1>

<p align="center">
  <b>Sistema backend distribuido para administración integral de una farmacia</b>
  <br>
  <em>Desarrollado con Spring Boot • MySQL/PostgreSQL • OpenAPI 3</em>
</p>

<p align="center">
  <a href="http://localhost:8080/swagger-ui/index.html">
    <img src="https://img.shields.io/badge/Documentación-SwaggerUI-brightgreen?style=for-the-badge&logo=swagger" alt="Swagger UI">
  </a>
  <a href="http://localhost:8080/v3/api-docs">
    <img src="https://img.shields.io/badge/API-OpenAPI3-orange?style=for-the-badge&logo=openapi-initiative" alt="OpenAPI 3">
  </a>
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot">
</p>

---

## 🌟 Características del Sistema

<div align="center">

| Característica | Icono | Descripción |
|----------------|-------|-------------|
<<<<<<< HEAD
| **Gestión Avanzada de Ventas** | 💰 | Control automático de stock y actualización de inventario |
| **Anulación Inteligente** | 🔄 | Cancelación de ventas con restauración de medicamentos |
| **Control de Inventario** | 📊 | Seguimiento en tiempo real de medicamentos y existencias |
| **Relaciones Sólidas** | 🔗 | Entidades interconectadas como `Venta` ↔ `DetalleVenta` |
| **DTOs Personalizados** | 🎯 | Exposición de datos desacoplada entre servicios |
| **Microservicios Independientes** | 🧩 | Separación clara de responsabilidades por dominio |
| **Escalabilidad y Modularidad** | 📦 | Arquitectura preparada para crecer sin fricciones |
=======
| **Usuario Admin Automático** | 👤 | Se crea al iniciar el sistema si no hay registros |
| **Gestión de Ventas** | 💰 | Actualiza stock automáticamente y genera factura |
| **Anulación de Ventas** | 🔄 | Restaura inventario al cancelar una venta |
| **Entidades Distribuidas** | 🔗 | Relaciones entre microservicios (`Cliente` ↔ `Receta`) |
| **Facturación Automática** | 🧾 | Generación de comprobantes de compra |
| **DTOs Personalizados** | 🎯 | Comunicación clara entre servicios y frontend |
| **Arquitectura Modular** | 🧩 | Microservicios independientes y escalables |
>>>>>>> 3ebe02948fd0564a726a5f117b674dc17f0b7388

</div>

---

<<<<<<< HEAD
## 📦 Microservicios del Sistema
=======
## 🧱 Microservicios y Funcionalidades
>>>>>>> 3ebe02948fd0564a726a5f117b674dc17f0b7388

<div align="center">

| Servicio | Icono | Descripción | Endpoints |
|----------|-------|-------------|-----------|
<<<<<<< HEAD
| **auth-service** | 🔐 | Gestión de empleados y usuarios | `GET/POST/PUT/DELETE /auth` |
| **customer-service** | 👥 | Gestión de clientes e historial | `GET/POST/PUT/DELETE /customer` |
| **catalog-service** | 💊 | Inventario de medicamentos | `GET/POST/PUT/DELETE /catalog` |
| **provider-service** | 🏢 | Gestión de proveedores | `GET/POST/PUT/DELETE /provider` |
| **sales-service** | 💰 | Registro de ventas y facturación | `GET/POST/PUT /sales` |
| **prescriptions-service** | 📝 | Gestión de recetas médicas | `GET/POST/PUT/DELETE /prescriptions` |
| **gateway-service** | 🌐 | Ruteo hacia microservicios | `/*` |
=======
| **auth-service** | 🔐 | Gestión de empleados y login | `GET/POST/PUT/DELETE /usuario` |
| **customer-service** | 👥 | Clientes e historial de recetas/ventas | `GET/POST/PUT /cliente` |
| **catalog-service** | 💊 | Medicamentos y stock | `GET/POST/PUT/DELETE /medicamento` |
| **provider-service** | 🏭 | Proveedores asociados a medicamentos | `GET/POST/PUT/DELETE /proveedor` |
| **sales-service** | 🧾 | Ventas, stock y facturación | `GET/POST /venta` |
| **prescriptions-service** | 📋 | Recetas médicas por cliente | `GET/POST /receta` |
| **gateway-service** | 🌐 | Ruteo hacia microservicios | `/api/**` |
>>>>>>> 3ebe02948fd0564a726a5f117b674dc17f0b7388
| **eureka-service** | 📡 | Descubrimiento de servicios | `Eureka Dashboard` |

</div>

---

## 🛠️ Tecnologías Utilizadas
<<<<<<< HEAD

<div align="center">

| Tecnología | Icono | Uso |
|------------|-------|-----|
| **Java 17** | <img src="https://img.shields.io/badge/Java-17-blue?style=flat&logo=openjdk" alt="Java 17"> | Lenguaje principal |
| **Spring Boot** | <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=flat&logo=springboot" alt="Spring Boot"> | Framework backend |
| **Spring Data JPA** | <img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat&logo=hibernate" alt="Spring Data JPA"> | Persistencia ORM |
| **Spring Cloud** | <img src="https://img.shields.io/badge/Spring_Cloud-Eureka/Gateway-6DB33F?style=flat&logo=spring"> | Eureka Client y Gateway |
| **MySQL / PostgreSQL** | <img src="https://img.shields.io/badge/SQL-MySQL/PostgreSQL-4479A1?style=flat&logo=mysql" alt="SQL"> | Base de datos relacional |
| **Lombok** | <img src="https://img.shields.io/badge/Lombok-Automático-FF9800?style=flat&logo=lombok" alt="Lombok"> | Reducción de boilerplate |
| **Maven** | <img src="https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven" alt="Maven"> | Gestión de dependencias |

</div>
=======
>>>>>>> 3ebe02948fd0564a726a5f117b674dc17f0b7388

<div align="center">

<<<<<<< HEAD
## 📝 Requerimientos Funcionales

<div align="center">

| Servicio | Funcionalidades | Estado |
|----------|-----------------|--------|
| **💊 Medicamentos** | CRUD completo • Control de stock • Búsqueda por nombre | ✅ Implementado |
| **👥 Clientes** | Gestión completa • Historial de compras y recetas | ✅ Implementado |
| **🔐 Empleados** | Roles (ADMIN, EMPLEADO) • Login integrado | ✅ Implementado |
| **🏢 Proveedores** | CRUD completo • Asociación con medicamentos | ✅ Implementado |
| **💰 Ventas** | Registro con control de stock • Anulación • Facturación | ✅ Implementado |
| **📝 Recetas Médicas** | Gestión completa • Asociación con clientes | ✅ Implementado |

</div>

---

## 📄 Documentación Técnica

<div align="center">

| Recurso | Enlace | Descripción |
|---------|--------|-------------|
| **📖 Swagger UI** | [Swagger](http://localhost:8080/swagger-ui/index.html) | Documentación interactiva |
=======
| Tecnología | Icono | Uso |
|------------|-------|-----|
| **Java 17** | <img src="https://img.shields.io/badge/Java-17-blue?style=flat&logo=openjdk" alt="Java 17"> | Lenguaje principal |
| **Spring Boot** | <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=flat&logo=springboot" alt="Spring Boot"> | Framework backend |
| **Spring Data JPA** | <img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat&logo=hibernate" alt="Spring Data JPA"> | Persistencia ORM |
| **Spring Cloud** | <img src="https://img.shields.io/badge/Spring_Cloud-Eureka/Gateway-6DB33F?style=flat&logo=spring"> | Eureka Client y Gateway |
| **MySQL / PostgreSQL** | <img src="https://img.shields.io/badge/SQL-MySQL/PostgreSQL-4479A1?style=flat&logo=mysql" alt="SQL"> | Base de datos relacional |
| **Lombok** | <img src="https://img.shields.io/badge/Lombok-Automático-FF9800?style=flat&logo=lombok" alt="Lombok"> | Reducción de boilerplate |
| **Maven** | <img src="https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven" alt="Maven"> | Gestión de dependencias |

>>>>>>> 3ebe02948fd0564a726a5f117b674dc17f0b7388
</div>

---

## ⚙️ Requerimientos No Funcionales

<div align="center">

| Categoría | Especificación | Estado |
|-----------|----------------|--------|
<<<<<<< HEAD
| **🛡️ Validaciones** | Mensajes claros y personalizados | ✅ Implementado |
| **📐 Modularidad** | Separación por microservicio | ✅ Implementado |
| **🔒 Seguridad** | Preparado para JWT y roles | ✅ Implementado |
| **📊 Performance** | Consultas optimizadas | ✅ Implementado |
=======
| **🛡️ Validaciones** | Entidades con mensajes claros y personalizados | ✅ Implementado |
| **📐 Modularidad** | Microservicios independientes y escalables | ✅ Implementado |
| **🔒 Seguridad** | Preparado para JWT y roles | ✅ Implementado |
| **📊 Performance** | Consultas optimizadas y control de stock | ✅ Implementado |
>>>>>>> 3ebe02948fd0564a726a5f117b674dc17f0b7388
| **🧼 Código Limpio** | Principios SOLID y buenas prácticas | ✅ Implementado |

</div>

---

<div align="center">

<<<<<<< HEAD
## 🚀 ¿Listo para Comenzar?

[**📖 Ir a la Documentación Interactiva**](http://localhost:8080/swagger-ui/index.html) •

**⭐ ¡No olvides darle una estrella al repo si te fue útil!**
=======
## 🚀 ¿Listo para Probarlo?

[**📖 Ir a la Documentación Interactiva**](http://localhost:8080/swagger-ui/index.html) • 

**⭐ ¡Dale una estrella al repo si te resultó útil!**
>>>>>>> 3ebe02948fd0564a726a5f117b674dc17f0b7388

---
*Desarrollado con ❤️ usando Spring Boot y Java 17*

<<<<<<< HEAD
</div>
=======
</div>
>>>>>>> 3ebe02948fd0564a726a5f117b674dc17f0b7388
