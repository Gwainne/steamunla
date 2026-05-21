# SteamUNLA

> Plataforma de distribución de videojuegos desarrollada con Spring Boot, inspirada en Steam. Trabajo Práctico — Universidad Nacional de Lanús.

---

## Tabla de contenidos

- [Funcionalidades](#funcionalidades)
- [Tech Stack](#tech-stack)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Ejecución](#ejecución)
- [Usuarios de prueba](#usuarios-de-prueba)
- [Resetear datos](#resetear-datos)
- [Estructura del proyecto](#estructura-del-proyecto)

---

## Funcionalidades

- Registro e inicio de sesión de usuarios
- Catálogo de juegos con búsqueda y filtros por género
- Sistema de recomendaciones personalizadas
- Compra de juegos con múltiples métodos de pago
- Biblioteca personal con juegos adquiridos
- Sistema de reseñas y calificaciones
- Promociones y descuentos en juegos
- Wishlist (lista de deseados)
- Panel de publicación para desarrolladores

---

## Tech Stack

| Capa | Tecnología |
|---|---|
| Backend | Java 21, Spring Boot 3.5 |
| Seguridad | Spring Security |
| ORM | Spring Data JPA, Hibernate |
| Base de datos | MySQL 8 |
| Vistas | Thymeleaf, HTML5, CSS3 |
| Build | Maven (incluido via `mvnw`) |

---

## Requisitos

| Herramienta | Versión mínima |
|---|---|
| JDK | 21 (Temurin recomendado) |
| MySQL | 8.x |
| VS Code | Última versión estable |

**Extensions de VS Code necesarias:**
- Extension Pack for Java
- Spring Boot Extension Pack

---

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/Gwainne/steamunla.git
cd steamunla
```

### 2. Crear la base de datos

Conectate a MySQL y ejecutá:

```sql
CREATE DATABASE steamunla
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

### 3. Configurar variables de entorno

Copiá el archivo de ejemplo y completá tus credenciales:

```bash
cp .env.example .env
```

Editá `.env` con tus datos:

```env
DB_USERNAME=root
DB_PASSWORD=tu_contraseña
```

> ⚠️ **Nunca subas el archivo `.env` al repositorio.** Ya está incluido en el `.gitignore`.

---

## Ejecución

### Opción A — VS Code (recomendado)

1. Abrí el proyecto en VS Code
2. Abrí el **Spring Boot Dashboard** en el panel lateral
3. Hacé click en ▶ **Run** sobre `SteamunlaApplication`

### Opción B — Terminal

```bash
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows
```

La aplicación queda disponible en `http://localhost:8080/games`.

Al iniciar por primera vez con la base vacía, el sistema carga automáticamente los datos de prueba. No hace falta correr ningún comando adicional.

---

## Usuarios de prueba

| Usuario | Contraseña | Descripción |
|---|---|---|
| test | test | Usuario normal con juegos en biblioteca |
| publisher | publisher | Desarrollador con juegos publicados |

---

## Resetear datos

Si los datos quedaron en un estado inconsistente o querés empezar de cero, ejecutá esto en MySQL y reiniciá la aplicación:

```sql
SET SQL_SAFE_UPDATES = 0;

DELETE FROM wishlists;
DELETE FROM reviews;
DELETE FROM purchases;
DELETE FROM user_library;
DELETE FROM promotions;
DELETE FROM games;
DELETE FROM users;

SET SQL_SAFE_UPDATES = 1;
```

Al reiniciar, el seed vuelve a cargar todo automáticamente.

---

## Estructura del proyecto

```
src/
└── main/
    ├── java/com/steamunla/steamunla/
    │   ├── config/          # Configuración de Spring Security
    │   ├── controller/      # Controllers MVC
    │   ├── model/           # Entidades JPA
    │   ├── repository/      # Repositorios Spring Data
    │   ├── service/         # Lógica de negocio
    │   └── DataInitializer  # Seed automático al iniciar
    └── resources/
        ├── templates/       # Vistas Thymeleaf
        │   ├── auth/
        │   ├── games/
        │   ├── library/
        │   ├── purchases/
        │   ├── promotions/
        │   └── reviews/
        └── application.properties
```