# 🔒 Configuración de Seguridad - FitManager

## 📋 Variables de Entorno Requeridas

### Para Desarrollo Local
```bash
# Crear archivo .env en la raíz del proyecto
DB_URL=jdbc:postgresql://localhost:5432/fitmanager
DB_USERNAME=postgres
DB_PASSWORD=tu_password_real
JWT_SECRET=clave-jwt-super-segura-minimo-32-caracteres
```

### Para Producción
```bash
# Configurar en el servidor/contenedor
export DB_URL="jdbc:postgresql://servidor-prod:5432/fitmanager_prod"
export DB_USERNAME="usuario_prod"
export DB_PASSWORD="password_ultra_seguro"
export JWT_SECRET="clave-jwt-produccion-muy-segura-64-caracteres-minimo"
export JPA_DDL_AUTO="validate"
export JPA_SHOW_SQL="false"
```

## 🚀 Cómo Ejecutar

### Desarrollo
```bash
# Con perfil de desarrollo
java -jar -Dspring.profiles.active=dev fitmanager.jar

# O con Maven
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Producción
```bash
# Con perfil de producción y variables de entorno
java -jar -Dspring.profiles.active=prod fitmanager.jar
```

## 🔑 Generar Clave JWT Segura

### Opción 1: OpenSSL
```bash
openssl rand -base64 64
```

### Opción 2: Java
```java
import javax.crypto.KeyGenerator;
import java.util.Base64;

KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
SecretKey secretKey = keyGen.generateKey();
String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
```

### Opción 3: Online
- Usar generadores online de claves aleatorias (mínimo 32 caracteres)

## ⚠️ IMPORTANTE - Nunca Subir al Repositorio

- ❌ Archivo `.env` con datos reales
- ❌ `application-local.properties` con credenciales
- ❌ Cualquier archivo con contraseñas reales
- ❌ Certificados o claves privadas

## ✅ Lo que SÍ puedes subir

- ✅ `.env.example` (plantilla sin datos reales)
- ✅ `application.properties` (solo con variables de entorno)
- ✅ `application-dev.properties` (datos de desarrollo)
- ✅ `application-prod.properties` (sin credenciales reales)

## 🛡️ Configuración por Entorno

| Configuración | Desarrollo | Producción |
|---------------|------------|------------|
| DDL Auto | create-drop | validate |
| Show SQL | true | false |
| Logs | DEBUG | INFO |
| JWT Timeout | 1 hora | 30 min |
| Error Details | Mostrar | Ocultar |
