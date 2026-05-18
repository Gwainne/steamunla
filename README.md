# SteamUNLA

> Plataforma de distribución de videojuegos desarrollada con Spring Boot, inspirada en Steam.

---

## Tabla de contenidos

- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecución](#ejecución)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Tecnologías](#tecnologías)

---

## Requisitos

| Herramienta | Versión mínima |
|---|---|
| JDK | 21 (Temurin recomendado) |
| MySQL | 8.x |
| Maven | 3.9.x (incluido via `mvnw`) |
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

La aplicación queda disponible en `http://localhost:8080`.

---

## Primeros pasos

1. Registrate en `http://localhost:8080/register`
2. Iniciá sesión en `http://localhost:8080/login`
3. Explorá el catálogo en `http://localhost:8080/games`

---

## Estructura del proyecto

```
src/
└── main/
    ├── java/com/steamunla/steamunla/
    │   ├── config/          # Configuración de Spring Security
    │   ├── controller/      # Controllers MVC
    │   ├── dto/             # Data Transfer Objects
    │   ├── exception/       # Manejo de excepciones
    │   ├── model/           # Entidades JPA
    │   ├── repository/      # Repositorios Spring Data
    │   └── service/         # Lógica de negocio
    └── resources/
        ├── templates/       # Vistas Thymeleaf
        │   ├── auth/
        │   ├── games/
        │   ├── library/
        │   ├── purchases/
        │   └── reviews/
        └── application.properties
```

---

## Tecnologías

- **Backend:** Java 21, Spring Boot 3.5, Spring Security, Spring Data JPA
- **Base de datos:** MySQL 8 + Hibernate
- **Frontend:** Thymeleaf, HTML5, CSS3
- **Build:** Maven