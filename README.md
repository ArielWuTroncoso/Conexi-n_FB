# AbrazaMente — Spring Boot + React

Aplicación integrada con:

- Backend Java 21, Spring Boot, Spring Security y JWT.
- Frontend React con Vite.
- PostgreSQL como base de datos relacional.
- Home, inicio de sesión y registro migrados a React.
- Terapia, Comunidad y Recursos clásicos conservados temporalmente en `public/legacy`.

La descripción completa del diagnóstico, los cambios, los contratos JSON y las comprobaciones está en [`MIGRACION_REACT.md`](MIGRACION_REACT.md).

## Estructura principal

```text
Backend-Acondicionado/
├── src/main/java/com/backend/abrazamente/  # API Spring Boot
├── src/main/resources/application.yaml     # Configuración backend
├── src/                                    # Aplicación React
├── public/                                 # Recursos y frontend clásico conservado
├── database/                               # Esquema y migración PostgreSQL
├── docker-compose.yml                      # PostgreSQL local
├── .env.example                            # Plantilla de variables
└── MIGRACION_REACT.md                      # Informe técnico completo
```

## Requisitos

- Java JDK 21.
- Node.js 20 o superior.
- Docker Desktop, o una instalación accesible de PostgreSQL.

## Inicio rápido

Desde `Backend-Acondicionado`:

```bash
cp .env.example .env
docker compose up -d postgres
npm ci
npm run dev
```

En otra terminal:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

En Windows PowerShell:

```powershell
Copy-Item .env.example .env
docker compose up -d postgres
npm ci
npm run dev
```

En otra terminal de PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Servicios de desarrollo:

- React: `http://localhost:5173`
- API: `http://localhost:8080`
- PostgreSQL: `localhost:5432`, base `db`

## Construcción

Hay dos formas de compilar el frontend, según cómo lo despliegues:

```bash
# Sitio estático independiente (Render Static Site, Vercel, Netlify) → dist/
npm run build

# Empaquetado dentro del backend (un solo JAR) → src/main/resources/static/
npm run build:spring
./mvnw clean package
java -jar target/abrazamente-0.0.1-SNAPSHOT.jar
```

## Despliegue en producción (Render + Neon)

Ver la guía completa, paso a paso, en [`GUIA_DESPLIEGUE.md`](GUIA_DESPLIEGUE.md):
creación de la base en Neon, variables de entorno, `render.yaml` incluido en
la raíz, `Dockerfile` opcional y checklist de verificación end-to-end.

## Credenciales de demostración

El esquema para una base nueva crea usuarios de muestra. Todos utilizan la contraseña:

```text
usuario123
```

Ejemplo:

```text
juan.garcia@example.com
```

Estas credenciales son solo para desarrollo. Cambia `JWT_SECRET`, las contraseñas y las credenciales de PostgreSQL antes de desplegar.
