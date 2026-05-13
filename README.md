# SteamUNLA - Setup

## Requisitos
- JDK 21 (Temurin)
- MySQL 8.x
- VS Code con Extension Pack for Java y Spring Boot Extension Pack

## Pasos para correr el proyecto

### 1. Clonar el repo
git clone https://github.com/Gwainne/steamunla.git

### 2. Crear la base de datos en MySQL
CREATE DATABASE steamunla CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

### 3. Configurar credenciales
Crear las variables de entorno:
- DB_USER = tu usuario de MySQL (generalmente root)
- DB_PASSWORD = tu contraseña de MySQL

### 4. Correr la aplicación
Desde VS Code: Spring Boot Dashboard → Run
