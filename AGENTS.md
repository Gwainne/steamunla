# Steamunla — Contexto para agente

## Descripción
Plataforma de distribución de videojuegos similar a Steam.
Stack: Spring Boot 3.5, Spring Security, Thymeleaf, JPA/Hibernate, MySQL 8, Lombok.

## Regla principal
SIEMPRE leer los archivos relevantes antes de modificarlos. Nunca asumir la estructura de modelos, campos o métodos existentes.

---

## Arquitectura
Controller → Service → Repository → Model. Sin DTOs.
El usuario logueado se obtiene SIEMPRE de HttpSession:
(User) session.getAttribute("loggedUser")
NO usar Spring Security principal para obtener el usuario.

---

## Estructura de paquetes
com.steamunla.steamunla
├── config/         → SecurityConfig (CSRF desactivado, anyRequest().permitAll())
├── controller/     → AuthController, GameController, PurchaseController,
│                     LibraryController, ReviewController, PromotionController
├── model/          → User, Game, Library, Purchase, Review, Promotion,
│                     Wishlist, GameUpdate
├── repository/     → Un repo por modelo, Spring Data JPA
│                     Preferir métodos derivados sobre @Query JPQL
├── service/        → GameService, UserService, PurchaseService, LibraryService,
│                     ReviewService, PromotionService, WishlistService,
│                     RecommendationService
└── DataInitializer.java → Seed, corre si libraryRepository.count() == 0

---

## Modelos — campos clave
User:        id, username, email, password, role, active, createdAt
Game:        id, title, description, genre, price, imageUrl, developerName,
             publisher(User), releaseDate, createdAt, active, averageRating, downloadCount
Library:     id, user, game, installed, addedAt
Purchase:    id, user, game, amountPaid, paymentMethod, purchaseDate, status
Promotion:   id, game, discountPercent, startDate, endDate, description, active
Wishlist:    id, user, game
Review:      id, user, game, rating, comment, createdAt

---

## Rutas principales
GET  /games               → tienda (todos los juegos, recomendaciones, discovery)
GET  /games/{id}          → detalle del juego
GET  /games/new           → formulario publicar juego
GET  /games/my-games      → juegos publicados por el usuario logueado
GET  /purchases/checkout/{gameId} → checkout
POST /purchases/confirm   → confirmar compra
GET  /purchases/success   → pantalla de éxito
GET  /library             → biblioteca personal
GET  /wishlist            → lista de deseados
GET  /promotions/new/{gameId} → formulario nueva promoción
POST /promotions/create   → crear promoción
POST /promotions/deactivate/{id} → desactivar promoción
GET  /auth/login o /login → login
POST /login               → procesar login
GET  /register            → registro
POST /register            → procesar registro
GET  /logout              → cerrar sesión

---

## Vistas (Thymeleaf)
templates/
├── auth/        → login.html, register.html
├── games/       → index.html (tienda), detail.html, new.html, my-games.html
├── purchases/   → checkout.html, purchase-success.html
├── library/     → index.html
├── reviews/     → new.html
├── promotions/  → new.html
└── wishlist/    → index.html

## Estilo visual
- Variables CSS: --bg (#0e1117), --surface (#161b26), --surface2 (#1e2536),
  --border (#2a3347), --accent (#4d9fff), --green (#4ade80), --text (#e2e8f0), --muted (#64748b)
- Fuentes: Rajdhani (títulos, font-family:'Rajdhani',sans-serif) + Inter (cuerpo)
- Cards con clase .card, grids con clase .grid (3 columnas, minmax 280px)
- Botones: .btn (secundario) y .btn.primary (gradiente azul)
- Todos los archivos HTML deben ser consistentes con este estilo

---

## Usuarios de prueba (seed)
- test / test         → usuario normal, tiene CS2, Elden Ring y Civ VI en biblioteca
- publisher / publisher → tiene todos los juegos como publisher

## Base de datos
- 22 juegos con portadas (Steam CDN), géneros: RPG, FPS, Strategy, Adventure, Sports, Horror
- 4 promociones activas: The Witcher 3 (50%), Cyberpunk 2077 (30%), FIFA 24 (20%), Dead by Daylight (25%)

---

## Comportamiento de la tienda (/games)
- Sección "Recomendado para vos": juegos del mismo género que los que tiene el usuario, que no estén en su biblioteca, ordenados por averageRating desc (máx 6)
- Sección "Descubrí algo nuevo": juegos de géneros que el usuario NO tiene, ordenados por averageRating desc (máx 6)
- Ambas secciones se ocultan cuando el usuario usa el buscador o filtra por género
- Filtros: buscador por texto + chips por género, todo en frontend con JS puro (sin tocar backend)
- El botón "Comprar" lleva a /games/{id} (detalle), no directo al checkout

## Comportamiento de compra
- Checkout verifica que el usuario esté logueado (si no, redirect /login)
- Verifica que no haya comprado el juego antes (si sí, redirect /games/{id} con errorMessage)
- POST /purchases/confirm → crea Purchase + agrega a Library → redirect /purchases/success

## Promociones
- Solo el publisher del juego puede crear promociones (/promotions/new/{gameId})
- Se muestran en la tienda y en el detalle: precio tachado + precio con descuento + badge "X% OFF"

---

## Convenciones importantes
- No usar @Query JPQL salvo que sea estrictamente necesario
- No romper métodos existentes al agregar nuevos
- Verificar imports antes de guardar cualquier archivo Java
- Las vistas no tienen layout compartido (cada HTML es standalone con su propio nav y CSS)
- Spring Security está configurado para permitir todo — no hay restricciones por rol implementadas
- Variables de entorno en .env (no commitear): DB_USERNAME, DB_PASSWORD