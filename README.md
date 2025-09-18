# 💊 Sistema de Gestión para Farmacia (Microservicios)

Sistema de backend distribuido para la administración integral de una farmacia.  
Permite gestionar empleados, clientes, medicamentos, ventas, proveedores y recetas médicas mediante una arquitectura de **microservicios**, optimizando y automatizando los procesos operativos con una API REST robusta, extensible y escalable.

---

## 🌟 Características del Sistema
- Autogeneración de usuario administrador al iniciar el sistema.  
- Gestión completa de ventas con actualización automática del stock de medicamentos.  
- Anulación de ventas con restauración del inventario.  
- Relación entre entidades distribuidas en microservicios (ej. `Cliente` ↔ `Receta`, `Venta` ↔ `DetalleVenta`).  
- Generación automática de facturas de compra.  
- Uso de **DTOs** para la comunicación entre microservicios y hacia el frontend.  
- Modularidad y separación de responsabilidades mediante microservicios independientes.  

---

## 🛠️ Tecnologías Utilizadas
- **Back-end (API REST)**  
- Java 17  
- Spring Boot  
  - Spring Web  
  - Spring Data JPA  
  - Spring Security (opcional, para futuras integraciones con auth-service)  
  - Spring Cloud Eureka Client  
  - Spring Cloud Gateway  
- Lombok  
- MySQL / PostgreSQL (configurable)  
- Maven  

---

## 📝 Microservicios y Funcionalidades

### **auth-service**
- Gestión de empleados/usuarios (`ADMIN`, `EMPLEADO`).  
- CRUD completo: alta, baja, edición y listado.  
- Login y autenticación (preparado para JWT).  
- Creación automática de un administrador si no existen registros.  

### **customer-service**
- Gestión completa de clientes.  
- Consulta del historial de ventas y recetas médicas asociadas.  

### **catalog-service**
- Gestión de medicamentos.  
- CRUD completo: alta, baja, edición y listado.  
- Control de stock, disponibilidad y categoría.  
- Asociación con proveedores (`proveedorId`).  

### **provider-service**
- Gestión de proveedores.  
- CRUD completo: alta, baja, edición y listado.  
- Asociación con medicamentos (`medicamentoId`).  

### **sales-service**
- Registro de ventas con detalle de cliente y medicamentos (referenciando `clienteId`, `medicamentoId`).  
- Actualización automática de stock al registrar la venta.  
- Anulación de ventas con devolución de stock.  
- Cálculo automático de totales y generación de factura.  

### **prescriptions-service**
- Gestión de recetas médicas.  
- Asociación de recetas a clientes (`clienteId`).  
- Consulta detallada por cliente o de manera general.  

### **gateway-service**
- Puerta de entrada al ecosistema de microservicios desde clientes externos (frontend, Postman, etc.).  
- Ruteo hacia los microservicios correspondientes.  

### **eureka-service**
- Registro y descubrimiento de microservicios (Service Discovery).  
- Permite que todos los servicios se localicen dinámicamente y habilita balanceo de carga y resiliencia.  

---

## ⚙️ Requerimientos No Funcionales
- Validaciones en entidades con mensajes claros y personalizados.  
- Modularidad y escalabilidad para futuras integraciones (web, mobile, auth-service).  
- Arquitectura preparada para implementar JWT en el futuro.  
- Código limpio y documentado siguiendo principios SOLID y buenas prácticas.  
- Uso de **DTOs** para desacoplar datos internos de la exposición hacia otros servicios o frontend.  

---
