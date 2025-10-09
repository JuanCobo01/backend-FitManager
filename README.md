# FitManager - Sistema de Gestión de Gimnasio

## 📋 Descripción

FitManager es un sistema backend desarrollado con Spring Boot para la gestión integral de un gimnasio. El sistema permite administrar usuarios, entrenadores, rutinas de ejercicios, seguimiento de progreso y gestión de pagos.

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA** - Para persistencia de datos
- **Spring Web** - Para API REST
- **MySQL 8** - Base de datos
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias

## 🏗️ Arquitectura del Sistema

El proyecto sigue el patrón de arquitectura MVC (Model-View-Controller) con las siguientes capas:

```
src/main/java/com/uceva/fitmanager/
├── controller/          # Controladores REST
├── service/            # Lógica de negocio
├── repository/         # Acceso a datos
├── model/             # Entidades JPA
└── FitmanagerApplication.java
```

## 📊 Modelo de Datos

### Entidades Principales

#### Usuario
- **Propósito**: Gestión de usuarios del gimnasio
- **Campos**: ID, nombre, correo, contraseña, edad, altura, peso inicial, fecha de registro
- **Relaciones**: 
  - Uno a muchos con Rutinas
  - Uno a muchos con Progresos  
  - Uno a muchos con Pagos

#### Entrenador
- **Propósito**: Gestión de entrenadores personales
- **Campos**: ID, nombre, correo, contraseña, especialidad
- **Relaciones**:
  - Uno a muchos con Rutinas
  - Uno a muchos con Pagos

#### Ejercicio
- **Propósito**: Catálogo de ejercicios disponibles
- **Campos**: ID, nombre del ejercicio, descripción, grupo muscular
- **Grupos Musculares**: Pecho, Espalda, Piernas, Hombros, Brazos, Core, Cardio
- **Relaciones**: Uno a muchos con DetalleRutina

#### Rutina
- **Propósito**: Rutinas de ejercicios asignadas a usuarios
- **Campos**: ID, nombre de rutina, descripción
- **Relaciones**: 
  - Muchos a uno con Usuario
  - Muchos a uno con Entrenador
  - Uno a muchos con DetalleRutina

#### DetalleRutina
- **Propósito**: Especificaciones de ejercicios en cada rutina
- **Campos**: ID, repeticiones, series
- **Relaciones**:
  - Muchos a uno con Rutina
  - Muchos a uno con Ejercicio

#### Progreso
- **Propósito**: Seguimiento del progreso físico de usuarios
- **Campos**: ID, fecha, peso, medida pecho, medida cintura, medida brazo
- **Relaciones**: Muchos a uno con Usuario

#### Pago
- **Propósito**: Gestión de pagos y suscripciones
- **Campos**: ID, fecha de pago, monto, método de pago, estado, tipo de suscripción
- **Estados**: Completado, Pendiente, Rechazado
- **Tipos de Suscripción**: Básico, Premium, Entrenador
- **Relaciones**: Muchos a uno con Usuario

#### Administrador
- **Propósito**: Gestión de administradores del sistema
- **Campos**: ID, nombre, correo, contraseña, rol

## 🚀 API Endpoints

### 👤 Usuarios (`/api/usuarios`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/usuarios` | Obtener todos los usuarios |
| GET | `/api/usuarios/{id}` | Obtener usuario por ID |
| POST | `/api/usuarios` | Crear nuevo usuario |
| PUT | `/api/usuarios/actualizar/{id}` | Actualizar usuario |
| DELETE | `/api/usuarios/borrar/{id}` | Eliminar usuario |
| POST | `/api/usuarios/login` | Login de usuario |

**Ejemplo Login:**
```json
{
  "correo": "juan.perez@email.com",
  "contrasena": "password123"
}
```

### 🏋️ Entrenadores (`/api/entrenadores`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/entrenadores` | Obtener todos los entrenadores |
| GET | `/api/entrenadores/buscar/{id}` | Obtener entrenador por ID |
| POST | `/api/entrenadores` | Crear nuevo entrenador |
| PUT | `/api/entrenadores/actualizar/{id}` | Actualizar entrenador |
| DELETE | `/api/entrenadores/borrar/{id}` | Eliminar entrenador |
| POST | `/api/entrenadores/login` | Login de entrenador |

### 💪 Ejercicios (`/api/ejercicios`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/ejercicios` | Obtener todos los ejercicios |
| GET | `/api/ejercicios/buscar/{id}` | Obtener ejercicio por ID |
| POST | `/api/ejercicios` | Crear nuevo ejercicio |
| PUT | `/api/ejercicios/actualizar/{id}` | Actualizar ejercicio |
| DELETE | `/api/ejercicios/borrar/{id}` | Eliminar ejercicio |
| GET | `/api/ejercicios/categoria/{grupoMuscular}` | Ejercicios por grupo muscular |

### 📋 Rutinas (`/api/rutinas`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/rutinas` | Obtener todas las rutinas |
| GET | `/api/rutinas/buscar/{id}` | Obtener rutina por ID |
| POST | `/api/rutinas` | Crear nueva rutina |
| PUT | `/api/rutinas/actualizar/{id}` | Actualizar rutina |
| DELETE | `/api/rutinas/borrar/{id}` | Eliminar rutina |
| GET | `/api/rutinas/usuario/{usuarioId}` | Rutinas por usuario |
| GET | `/api/rutinas/entrenador/{entrenadorId}` | Rutinas por entrenador |

### 📈 Progresos (`/api/progresos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/progresos` | Obtener todos los progresos |
| GET | `/api/progresos/buscar/{id}` | Obtener progreso por ID |
| POST | `/api/progresos` | Crear nuevo progreso |
| PUT | `/api/progresos/actualizar/{id}` | Actualizar progreso |
| DELETE | `/api/progresos/borrar/{id}` | Eliminar progreso |
| GET | `/api/progresos/usuario/{usuarioId}` | Progresos por usuario |
| GET | `/api/progresos/fecha/{fecha}` | Progresos por fecha |
| GET | `/api/progresos/usuario/{usuarioId}/fecha/{fecha}` | Progreso específico por usuario y fecha |

### 💳 Pagos (`/api/pagos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/pagos` | Obtener todos los pagos |
| GET | `/api/pagos/buscar/{id}` | Obtener pago por ID |
| POST | `/api/pagos` | Crear nuevo pago |
| GET | `/api/pagos/usuario/{usuarioId}` | Pagos por usuario |
| GET | `/api/pagos/entrenador/{entrenadorId}` | Pagos por entrenador |
| GET | `/api/pagos/fecha/{fecha}` | Pagos por fecha |
| GET | `/api/pagos/estado/{estado}` | Pagos por estado |

## ⚙️ Configuración e Instalación

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Configuración de Base de Datos

1. **Crear la base de datos:**
```sql
CREATE DATABASE fitmanager;
```

2. **Configurar conexión en `application.properties`:**
```properties
# Configuración de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/fitmanager?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_password

# Configuración de JPA / Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Instalación y Ejecución

1. **Clonar el repositorio:**
```bash
git clone <url-del-repositorio>
cd backend-FitManager
```

2. **Instalar dependencias:**
```bash
./mvnw clean install
```

3. **Ejecutar la aplicación:**
```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Datos de Prueba

El proyecto incluye un archivo `database_test_data.sql` con datos de prueba que contiene:
- 8 usuarios de ejemplo
- 6 entrenadores especializados
- 25 ejercicios categorizados
- 16 rutinas variadas
- Múltiples registros de progreso
- Historial de pagos

**Para cargar los datos de prueba:**
1. Ejecutar la aplicación para que JPA cree las tablas
2. Ejecutar el script SQL en MySQL:
```bash
mysql -u root -p fitmanager < database_test_data.sql
```

## 🧪 Pruebas de la API

### Ejemplos con cURL

**Obtener todos los usuarios:**
```bash
curl -X GET http://localhost:8080/api/usuarios
```

**Login de usuario:**
```bash
curl -X POST http://localhost:8080/api/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{"correo":"juan.perez@email.com","contrasena":"password123"}'
```

**Obtener rutinas de un usuario:**
```bash
curl -X GET http://localhost:8080/api/rutinas/usuario/1
```

**Crear un nuevo progreso:**
```bash
curl -X POST http://localhost:8080/api/progresos \
  -H "Content-Type: application/json" \
  -d '{"fecha":"2024-03-20","peso":75.5,"medidaPecho":95.0,"medidaCintura":82.0,"medidaBrazo":33.0,"usuario":{"idUsuario":1}}'
```

## 📁 Estructura de Archivos

```
backend-FitManager/
├── src/
│   ├── main/
│   │   ├── java/com/uceva/fitmanager/
│   │   │   ├── controller/           # Controladores REST
│   │   │   │   ├── usuarioController.java
│   │   │   │   ├── entrenadorController.java
│   │   │   │   ├── ejercicioController.java
│   │   │   │   ├── rutinaController.java
│   │   │   │   ├── detalleRutinaController.java
│   │   │   │   ├── progresoController.java
│   │   │   │   ├── pagoController.java
│   │   │   │   └── administradorController.java
│   │   │   ├── service/              # Servicios de negocio
│   │   │   ├── repository/           # Repositorios JPA
│   │   │   ├── model/               # Entidades JPA
│   │   │   └── FitmanagerApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── database_test_data.sql           # Datos de prueba
├── pom.xml                         # Configuración Maven
├── mvnw & mvnw.cmd                 # Maven Wrapper
└── README.md
```

## 🌟 Características Implementadas

### ✅ Funcionalidades Completadas

- **CRUD completo** para todas las entidades
- **Sistema de autenticación** básico para usuarios y entrenadores
- **Gestión de rutinas personalizadas** con detalles de ejercicios
- **Seguimiento de progreso** con medidas corporales
- **Sistema de pagos** con múltiples estados y tipos de suscripción
- **Consultas especializadas** por fecha, usuario, entrenador, etc.
- **Relaciones JPA** correctamente mapeadas
- **CORS habilitado** para integración frontend
- **Datos de prueba** completos y realistas

### 🔄 API Features

- **RESTful Design**: Endpoints siguiendo principios REST
- **JSON Response**: Todas las respuestas en formato JSON
- **Error Handling**: Manejo básico de errores con ResponseEntity
- **Cross-Origin**: CORS configurado para desarrollo frontend
- **Data Validation**: Validación a través de JPA constraints

## 🔧 Configuración Avanzada

### Variables de Entorno

Para producción, considera usar variables de entorno:

```properties
# Base de datos
DB_URL=${DB_URL:jdbc:mysql://localhost:3306/fitmanager}
DB_USERNAME=${DB_USERNAME:root}
DB_PASSWORD=${DB_PASSWORD:}

# JPA
JPA_DDL_AUTO=${JPA_DDL_AUTO:validate}
JPA_SHOW_SQL=${JPA_SHOW_SQL:false}
```

### Profiles de Spring

Puedes crear diferentes profiles para desarrollo y producción:

- `application-dev.properties`
- `application-prod.properties`

## 📝 Próximas Mejoras

### 🔮 Funcionalidades Futuras

- [ ] **Seguridad JWT**: Implementar Spring Security con JWT
- [ ] **Validación de datos**: Bean Validation con anotaciones
- [ ] **Documentación API**: Integración con Swagger/OpenAPI
- [ ] **Testing**: Unit tests y Integration tests
- [ ] **Logging**: Sistema de logs estructurado
- [ ] **Paginación**: Implementar paginación en consultas
- [ ] **Filtros avanzados**: Búsquedas más complejas
- [ ] **Notificaciones**: Sistema de notificaciones push
- [ ] **Reports**: Generación de reportes de progreso
- [ ] **File Upload**: Subida de imágenes de perfil

## 👥 Contribución

Este proyecto es parte del desarrollo académico de FitManager para la gestión integral de gimnasios.

### Equipo de Desarrollo

- **Backend Developer**: Desarrollo de APIs REST con Spring Boot
- **Database Designer**: Diseño y modelado de base de datos
- **Business Analyst**: Definición de requerimientos funcionales

---

## 📊 Resumen de Datos de Prueba

La base de datos incluye:
- **8 Usuarios** con perfiles diversos (edad 24-35 años)
- **6 Entrenadores** con especialidades variadas
- **25 Ejercicios** organizados por grupos musculares
- **16 Rutinas** personalizadas por usuario y entrenador
- **35+ Registros de progreso** con seguimiento temporal
- **15+ Pagos** con diferentes estados y métodos

---

*Última actualización: Octubre 2024*
*Versión: 0.0.1-SNAPSHOT*