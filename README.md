# RacimoSano — Detección de plagas en racimos de uva

Proyecto académico (UTP) inspirado en la app **Proagro / Agromas Digital Cloud**.

## Qué cambió en esta versión

- **Lombok** en todas las entidades (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`) — cero getters/setters/constructores escritos a mano.
- **`Reporte` ahora es una entidad JPA real**, persistida con Spring Data JPA (H2 en memoria por defecto; el driver de MySQL ya está en el `pom.xml` para cuando se quiera migrar).
- **Fecha automática**: `fechaDeteccion` ya no se pide en el formulario; el backend la asigna con `LocalDateTime.now()` al crear el reporte.
- **`pom.xml` robusto**: Web, Data JPA, Validation, Security (base permisiva), Lombok, H2, MySQL driver.
- **Tailwind CSS** (vía CDN) en todo el frontend, mobile-first y responsivo.
- **Formulario de reporte actualizado**:
  - Se quitó "Nombre completo" (la autoría se tomará del usuario autenticado más adelante).
  - "Variedad de uva" y "Lote" ahora son `<select>` (el de Lote se llena dinámicamente desde `/api/lotes`).
  - "Nivel de severidad" con las 4 opciones exactas pedidas (Nivel I a IV).
  - Dos campos de subida de archivos: foto de la plaga detectada y foto de los racimos afectados.

## Estructura del proyecto

```
vid-plagas/
├── pom.xml
├── src/main/java/pe/utp/vidplagas/
│   ├── VidPlagasApplication.java
│   ├── model/
│   │   ├── Plaga.java        (Lombok, catálogo en memoria)
│   │   ├── Lote.java         (Lombok, catálogo en memoria)
│   │   └── Reporte.java      (Lombok + @Entity JPA, con fecha automática)
│   ├── repository/
│   │   └── ReporteRepository.java   (Spring Data JPA)
│   ├── controller/
│   │   ├── PlagaController.java     -> /api/plagas, /api/estado
│   │   ├── LoteController.java      -> /api/lotes
│   │   └── ReporteController.java   -> /api/reportes (GET/POST multipart)
│   └── config/
│       ├── SecurityConfig.java      (base permisiva de Spring Security)
│       └── FileStorageConfig.java   (sirve /uploads/** desde disco)
└── src/main/resources/
    ├── application.properties
    └── static/
        ├── index.html        (Tailwind CDN)
        ├── reportar.html     (Tailwind CDN, nuevos campos + archivos)
        ├── js/app.js
        └── img/*.svg
```

## Cómo ejecutarlo

Requisitos: **Java 17+** y **Maven**.

```bash
cd vid-plagas
mvn spring-boot:run
```

- `http://localhost:8080/` → catálogo
- `http://localhost:8080/reportar.html` → formulario de reporte
- `http://localhost:8080/h2-console` → consola de H2 (JDBC URL: `jdbc:h2:mem:vidplagasdb`, usuario `sa`, sin contraseña) para ver la tabla `reportes`
- Las fotos subidas quedan en la carpeta `uploads/` (se crea sola en la raíz del proyecto) y son accesibles en `http://localhost:8080/uploads/<archivo>`

## Comprobación con `curl`

```bat
curl http://localhost:8080/api/estado
curl http://localhost:8080/api/plagas
curl http://localhost:8080/api/lotes
curl http://localhost:8080/api/reportes
```

Registrar un reporte (ahora es `multipart/form-data`, con o sin fotos; la fecha la pone el backend):

```bat
curl -X POST http://localhost:8080/api/reportes ^
  -F "lote=Lote 1 - Sector Norte" ^
  -F "variedadUva=Red Globe" ^
  -F "plagaDetectada=Oidio" ^
  -F "nivelSeveridad=Nivel II - Medio" ^
  -F "racimosAfectados=12" ^
  -F "observaciones=Visto en hilera 3" ^
  -F "evidenciaPlaga=@C:\ruta\foto-plaga.jpg" ^
  -F "evidenciaRacimos=@C:\ruta\foto-racimo.jpg"
```

(Los archivos son opcionales: se puede omitir cualquiera de los dos `-F "evidencia...=@..."` y el reporte se registra igual.)

## Notas y siguientes pasos

- **Tailwind vía CDN**: es el `<script src="https://cdn.tailwindcss.com">` (Play CDN), ideal para desarrollo/demo sin paso de compilación. Para producción se recomienda instalar Tailwind con su CLI/PostCSS y generar un `.css` final más liviano.
- **Spring Security** está agregado pero deliberadamente abierto (`permitAll()`), para no romper el envío del formulario ni el `curl` mientras no exista login. Cuando se implemente autenticación, `SecurityConfig` es el punto donde se restringen rutas y se retoma el patrón de doble `SecurityFilterChain` (sesión + JWT) que ya usaste en el proyecto "security" de adopción de mascotas.
- **MySQL**: el driver ya está en el `pom.xml`; para usarlo solo hay que descomentar el bloque correspondiente en `application.properties` y comentar el de H2.
- **Autoría del reporte**: cuando se agregue login, se puede añadir un campo `usuario` a `Reporte` tomado del `Authentication` actual, en vez de un input libre.
## Cambio de prueba realizado el Tue Sep 22 19:05:04 HPS 2026
