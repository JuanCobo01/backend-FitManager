# FitManager - Sistema de Gestión de Gimnasio

## 📋 Descripción

FitManager es un sistema backend desarrollado con Spring Boot para la gestión integral de un gimnasio. El sistema permite administrar usuarios, entrenadores, rutinas de ejercicios, seguimiento de progreso y gestión de pagos.

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA** - Para persistencia de datos
- **Spring Security** - Autenticación y autorización
- **Spring Web** - Para API REST
- **PostgreSQL 12+** - Base de datos
- **BCrypt** - Encriptación de contraseñas
- **JWT (JJWT 0.12.3)** - Tokens de autenticación
- **Bean Validation** - Validación de datos
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

### � Autenticación (`/v1/auth`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/v1/auth/usuario/login` | Login de usuario |
| POST | `/v1/auth/entrenador/login` | Login de entrenador |
| POST | `/v1/auth/administrador/login` | Login de administrador |
| POST | `/v1/auth/usuario/register` | Registro de nuevo usuario |
| POST | `/v1/auth/entrenador/register` | Registro de nuevo entrenador |
| POST | `/v1/auth/change-password` | Cambiar contraseña (requiere autenticación) |
| POST | `/v1/auth/logout` | Cerrar sesión |
| POST | `/v1/auth/refresh-activity` | Refrescar actividad de sesión |

**Ejemplo Login:**
```json
{
  "email": "juan@email.com",
  "password": "password123"
}
```

**Respuesta Login:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userType": "USUARIO",
  "userId": 1,
  "userName": "Juan Pérez",
  "email": "juan@email.com",
  "message": "Login exitoso"
}
```

**Ejemplo Registro:**
```json
{
  "nombre": "Juan Pérez",
  "email": "juan@email.com",
  "password": "password123",
  "edad": 25,
  "altura": 1.75,
  "pesoInicial": 70.5
}
```

### 👤 Usuarios (`/v1/usuarios`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/v1/usuarios` | Obtener todos los usuarios |
| GET | `/v1/usuarios/paginado` | Obtener usuarios con paginación |
| GET | `/v1/usuarios/{id}` | Obtener usuario por ID |
| POST | `/v1/usuarios` | Crear nuevo usuario |
| PUT | `/v1/usuarios/actualizar/{id}` | Actualizar usuario |
| DELETE | `/v1/usuarios/borrar/{id}` | Eliminar usuario |

**Ejemplo Paginación:**
```bash
GET /v1/usuarios/paginado?page=0&size=10&sort=nombre,asc
```

### 🏋️ Entrenadores (`/v1/entrenadores`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/v1/entrenadores` | Obtener todos los entrenadores |
| GET | `/v1/entrenadores/buscar/{id}` | Obtener entrenador por ID |
| POST | `/v1/entrenadores` | Crear nuevo entrenador |
| PUT | `/v1/entrenadores/actualizar/{id}` | Actualizar entrenador |
| DELETE | `/v1/entrenadores/borrar/{id}` | Eliminar entrenador |

### 💪 Ejercicios (`/v1/ejercicios`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/v1/ejercicios` | Obtener todos los ejercicios |
| GET | `/v1/ejercicios/buscar/{id}` | Obtener ejercicio por ID |
| POST | `/v1/ejercicios` | Crear nuevo ejercicio |
| PUT | `/v1/ejercicios/actualizar/{id}` | Actualizar ejercicio |
| DELETE | `/v1/ejercicios/borrar/{id}` | Eliminar ejercicio |
| GET | `/v1/ejercicios/categoria/{grupoMuscular}` | Ejercicios por grupo muscular |

### 📋 Rutinas (`/v1/rutinas`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/v1/rutinas` | Obtener todas las rutinas |
| GET | `/v1/rutinas/buscar/{id}` | Obtener rutina por ID |
| POST | `/v1/rutinas` | Crear nueva rutina |
| PUT | `/v1/rutinas/actualizar/{id}` | Actualizar rutina |
| DELETE | `/v1/rutinas/borrar/{id}` | Eliminar rutina |
| GET | `/v1/rutinas/usuario/{usuarioId}` | Rutinas por usuario |
| GET | `/v1/rutinas/entrenador/{entrenadorId}` | Rutinas por entrenador |

### 📈 Progresos (`/v1/progresos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/v1/progresos` | Obtener todos los progresos |
| GET | `/v1/progresos/buscar/{id}` | Obtener progreso por ID |
| POST | `/v1/progresos` | Crear nuevo progreso |
| PUT | `/v1/progresos/actualizar/{id}` | Actualizar progreso |
| DELETE | `/v1/progresos/borrar/{id}` | Eliminar progreso |
| GET | `/v1/progresos/usuario/{usuarioId}` | Progresos por usuario |
| GET | `/v1/progresos/fecha/{fecha}` | Progresos por fecha |
| GET | `/v1/progresos/usuario/{usuarioId}/fecha/{fecha}` | Progreso específico por usuario y fecha |

### 💳 Pagos (`/v1/pagos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/v1/pagos` | Obtener todos los pagos |
| GET | `/v1/pagos/buscar/{id}` | Obtener pago por ID |
| POST | `/v1/pagos` | Crear nuevo pago |
| GET | `/v1/pagos/usuario/{usuarioId}` | Pagos por usuario |
| GET | `/v1/pagos/entrenador/{entrenadorId}` | Pagos por entrenador |
| GET | `/v1/pagos/fecha/{fecha}` | Pagos por fecha |
| GET | `/v1/pagos/estado/{estado}` | Pagos por estado |

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
# Servidor
server.port=9090
server.servlet.context-path=/fitmanager

# Base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/fitmanager
spring.datasource.username=postgres
spring.datasource.password=tu_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT Configuration
jwt.secret=fitmanager-secret-key-2025-super-secure-token
jwt.expiration=1800000
jwt.inactivity-timeout=1800000
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

La aplicación estará disponible en: `http://localhost:9090/fitmanager/v1`

### 🔐 Seguridad

El sistema implementa las siguientes medidas de seguridad:

- **Encriptación BCrypt**: Todas las contraseñas se almacenan encriptadas
- **JWT Tokens**: Autenticación basada en tokens con expiración de 30 minutos
- **Session Management**: Control de actividad e inactividad de sesión
- **@JsonIgnore**: Las contraseñas nunca se exponen en respuestas JSON
- **Bean Validation**: Validación robusta de todos los datos de entrada
- **Role-based Access**: Control de acceso basado en roles (USUARIO, ENTRENADOR, ADMIN)

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
2. Ejecutar el script SQL en PostgreSQL:
```bash
psql -U postgres -d fitmanager -f database_test_data.sql
```

⚠️ **Nota**: Debido a la encriptación BCrypt, deberás crear nuevos usuarios a través del endpoint de registro o actualizar las contraseñas en la base de datos.

## 🧪 Pruebas de la API

### Ejemplos con cURL

**Obtener todos los usuarios:**
```bash
curl -X GET http://localhost:9090/fitmanager/v1/usuarios
```

**Registro de usuario:**
```bash
curl -X POST http://localhost:9090/fitmanager/v1/auth/usuario/register \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan Pérez","email":"juan@email.com","password":"password123","edad":25,"altura":1.75,"pesoInicial":70.5}'
```

**Login de usuario:**
```bash
curl -X POST http://localhost:9090/fitmanager/v1/auth/usuario/login \
  -H "Content-Type: application/json" \
  -d '{"email":"juan@email.com","password":"password123"}'
```

**Obtener rutinas de un usuario (con autenticación):**
```bash
curl -X GET http://localhost:9090/fitmanager/v1/rutinas/usuario/1 \
  -H "Authorization: Bearer <tu-token-jwt>"
```

**Crear un nuevo progreso:**
```bash
curl -X POST http://localhost:9090/fitmanager/v1/progresos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <tu-token-jwt>" \
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
- **Sistema de autenticación JWT** con Spring Security
- **Encriptación BCrypt** para contraseñas
- **Bean Validation** con validaciones robustas
- **Manejo global de excepciones** con respuestas consistentes
- **Gestión de rutinas personalizadas** con detalles de ejercicios
- **Seguimiento de progreso** con medidas corporales
- **Sistema de pagos** con múltiples estados y tipos de suscripción
- **Consultas especializadas** por fecha, usuario, entrenador, etc.
- **Paginación** en endpoints de listado
- **Relaciones JPA** correctamente mapeadas
- **CORS habilitado** para integración frontend
- **Datos de prueba** completos y realistas

### 🔄 API Features

- **RESTful Design**: Endpoints siguiendo principios REST
- **JSON Response**: Todas las respuestas en formato JSON
- **Error Handling**: Sistema global de manejo de excepciones con formato estandarizado
- **Security**: JWT tokens, BCrypt, @JsonIgnore en campos sensibles
- **Cross-Origin**: CORS configurado para desarrollo frontend
- **Data Validation**: Bean Validation con @Valid, @NotBlank, @Email, @Size, etc.
- **Pagination**: Soporte de paginación con Spring Data Pageable

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

- [ ] **Refresh Tokens**: Implementar tokens de refresco para sesiones más largas
- [ ] **Forgot Password**: Endpoint para recuperación de contraseña por email
- [ ] **Rate Limiting**: Protección contra ataques de fuerza bruta
- [ ] **Documentación API**: Integración con Swagger/OpenAPI
- [ ] **Testing**: Unit tests y Integration tests con JUnit y Mockito
- [ ] **Logging**: Sistema de logs estructurado con SLF4J
- [ ] **Auditoría**: Tracking de cambios (quién modificó qué y cuándo)
- [ ] **Redis Cache**: Caché para mejorar rendimiento
- [ ] **File Upload**: Subida de imágenes de perfil y progreso
- [ ] **Reports**: Generación de reportes PDF de progreso
- [ ] **Notificaciones**: Sistema de notificaciones push
- [ ] **OAuth2**: Login social (Google, Facebook)
- [ ] **Microservicios**: Migración a arquitectura de microservicios
- [ ] **WebSockets**: Notificaciones en tiempo real

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

*Última actualización: Noviembre 2025*
*Versión: 0.1.0-SNAPSHOT*