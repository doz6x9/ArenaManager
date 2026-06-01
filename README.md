# ArenaManager

ArenaManager is a Java 21 and Spring Boot esports tournament platform for managing LAN events, team rosters, brackets, match scores, and live operational metrics.

It has two faces:

- A Thymeleaf web dashboard for organizers, captains, and players.
- A JWT-secured REST API for API consumers and team-facing tournament data.

The project is built to demonstrate a layered backend architecture with clean controller/service/repository separation, JPA relationships, DTO boundaries, MapStruct mappers, role-based security, audit logs, Docker packaging, and GitLab CI.

## Current Stack

| Area | Technology |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 3.3.5 |
| Build | Maven Wrapper, Maven 3.9+ |
| Web UI | Thymeleaf, custom CSS |
| API | Spring MVC REST, OpenAPI/Swagger |
| Security | Spring Security, form login, JWT |
| Persistence | Spring Data JPA |
| Database | H2 in-memory database |
| Mapping | MapStruct 1.6.3 |
| Utilities | Lombok, Jakarta Validation |
| Packaging | Docker multi-stage build |
| CI | GitLab CI |

## Features

- Organizer dashboard for tournaments, teams, match control, metrics, and audit activity.
- Captain dashboard for registered squads, live brackets, and readable API route access.
- Player dashboard for tournament visibility, roster context, and read-only event data.
- Public self-registration for new player logins and profile metadata.
- JWT token endpoint for REST clients.
- Role-based access control for organizer, captain, and player workflows.
- Tournament registration, bracket generation, score updates, and winner tracking.
- Roster rules that prevent overfilled teams and invalid player assignments.
- DTO and mapper layer to avoid leaking JPA graphs directly through JSON.
- Audit log records for score and bracket operations.
- Swagger UI for API inspection.
- H2 console for local database inspection.
- Dockerfile using Java 21 runtime.
- GitLab pipeline for test and package stages.

## Demo Accounts

All demo users use the same password:

```text
password
```

| Username | Role | Default Landing Page |
| --- | --- | --- |
| `organizer` | `ORGANIZER`, `CAPTAIN` | `/admin/dashboard` |
| `captain` | `CAPTAIN` | `/captain/dashboard` |
| `player` | `PLAYER` | `/player/dashboard` |

Login page:

```text
http://localhost:8080/login
```

Create a new player account:

```text
http://localhost:8080/register
```

## Main Web Routes

| Route | Access | Purpose |
| --- | --- | --- |
| `/` | Public | Redirects users toward the right entry point |
| `/login` | Public | Form login |
| `/register` | Public | Create a player login and profile |
| `/admin/dashboard` | Organizer | Main operations dashboard |
| `/captain/dashboard` | Captain | Captain view of events, squads, and API routes |
| `/player/dashboard` | Player | Player view of events and read-only data |
| `/tournaments` | Organizer, Captain, Player | Tournament list |
| `/tournaments/{id}/bracket` | Organizer, Captain, Player | Bracket page |
| `/tournaments/{id}/bracket/generate` | Organizer | Generate first-round bracket |
| `/matches/score` | Organizer | Submit match score updates |
| `/api-routes` | Authenticated | Redirects by role |
| `/admin/api-routes` | Organizer | Full API route catalog |
| `/captain/api-routes` | Captain | Captain-visible API route catalog |
| `/player/api-routes` | Player | Player-visible API route catalog |

## REST API

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI docs:

```text
http://localhost:8080/v3/api-docs
```

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `POST` | `/api/auth/token` | Public | Issue a JWT bearer token |
| `POST` | `/api/auth/register` | Public | Create a player login/profile and return a JWT |
| `GET` | `/api/tournaments` | Player, Captain, Organizer | List tournaments |
| `GET` | `/api/tournaments/{id}` | Player, Captain, Organizer | Fetch one tournament |
| `GET` | `/api/tournaments/{id}/bracket` | Player, Captain, Organizer | Fetch bracket data |
| `POST` | `/api/tournaments` | Organizer | Create a tournament |
| `POST` | `/api/tournaments/{id}/teams/{teamId}` | Organizer | Register a team |
| `POST` | `/api/tournaments/{id}/bracket` | Organizer | Generate bracket |
| `GET` | `/api/teams` | Player, Captain, Organizer | List teams |
| `GET` | `/api/teams/{id}` | Player, Captain, Organizer | Fetch team roster |
| `POST` | `/api/teams` | Organizer | Create team |
| `POST` | `/api/teams/{teamId}/players/{playerId}` | Organizer | Add player to team |
| `GET` | `/api/players` | Player, Captain, Organizer | List players |
| `GET` | `/api/players/{id}` | Player, Captain, Organizer | Fetch player profile |
| `POST` | `/api/players` | Organizer | Create player |
| `GET` | `/api/matches?tournamentId={id}` | Player, Captain, Organizer | List tournament matches |
| `POST` | `/api/matches` | Organizer | Create match manually |
| `PUT` | `/api/matches/{id}/score` | Organizer | Update score and winner |
| `GET` | `/api/reports/dashboard` | Organizer | Dashboard metrics |
| `GET` | `/api/reports/audit` | Organizer | Recent audit events |

## Local Setup on Windows

Required:

- Java 21 JDK
- Docker Desktop, only if you want to run the container

Optional:

- Global Maven install. The project includes `mvnw.cmd`, so Maven does not need to be installed globally.

Check Java:

```powershell
java -version
```

Expected major version:

```text
21
```

The app includes a demo JWT signing secret so it can run immediately for local evaluation. For a real deployment, override it with `ARENA_JWT_SECRET`.

Run tests:

```powershell
.\mvnw.cmd test
```

Run the app locally:

```powershell
.\mvnw.cmd spring-boot:run
```

Then open:

```text
http://localhost:8080
```

## H2 Database

The app uses an in-memory H2 database by default. Demo data is loaded on startup.

H2 console:

```text
http://localhost:8080/h2-console
```

Use these values:

| Field | Value |
| --- | --- |
| JDBC URL | `jdbc:h2:mem:arenadb` |
| Username | `sa` |
| Password | leave blank |

The database resets when the application stops.

## JWT Example

The JWT signing key has a demo default in `application.yml`, so no environment variable is required for local evaluation.

For production, override these environment variables:

```text
ARENA_JWT_SECRET
ARENA_JWT_EXPIRATION_MS
```

The committed `.env.example` file shows the optional variable names. The real `.env` file is ignored by Git.

PowerShell:

```powershell
$tokenResponse = Invoke-RestMethod `
  -Method Post `
  -Uri "http://localhost:8080/api/auth/token" `
  -ContentType "application/json" `
  -Body '{"username":"captain","password":"password"}'

$tokenResponse.token
```

Use the token:

```powershell
Invoke-RestMethod `
  -Method Get `
  -Uri "http://localhost:8080/api/tournaments" `
  -Headers @{ Authorization = "Bearer $($tokenResponse.token)" }
```

Bash/cURL:

```bash
curl -X POST http://localhost:8080/api/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username":"captain","password":"password"}'
```

Register a new player account and receive a JWT:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newplayer",
    "email": "newplayer@example.com",
    "password": "password123",
    "confirmPassword": "password123",
    "preferredPeripheralDpi": 800,
    "mouseGripStyle": "Claw",
    "bio": "Duelist player looking for LAN brackets."
  }'
```

## Docker

The Dockerfile uses a multi-stage build:

- Build image: `maven:3.9-eclipse-temurin-21`
- Runtime image: `amazoncorretto:21-alpine`

Build the image:

```powershell
docker build -t arena-manager .
```

Run the container:

```powershell
docker run --name arena-manager -p 8080:8080 arena-manager
```

Open:

```text
http://localhost:8080
```

Run in the background:

```powershell
docker run -d --name arena-manager -p 8080:8080 arena-manager
```

View logs:

```powershell
docker logs -f arena-manager
```

Stop the container:

```powershell
docker stop arena-manager
```

Remove the stopped container:

```powershell
docker rm arena-manager
```

Run on another host port if `8080` is busy:

```powershell
docker run --name arena-manager -p 8081:8080 arena-manager
```

Then open:

```text
http://localhost:8081
```

### Docker Compose

Docker Compose is the easiest way to run the current single-container setup:

```powershell
docker compose up --build
```

Open:

```text
http://localhost:8080
```

Stop and remove the container:

```powershell
docker compose down
```

The project currently uses H2, so Compose only starts the Spring Boot service. If MySQL is added later, Compose can be extended with a database service.



## GitLab CI

The pipeline is defined in `.gitlab-ci.yml`.

It runs two stages:

| Stage | Command | Output |
| --- | --- | --- |
| `test` | `mvn test` | Runs the test suite |
| `package` | `mvn -DskipTests package` | Builds the application JAR |

The package stage keeps the generated JAR from:

```text
target/*.jar
```

## Architecture

```text
Controller -> Service -> Repository -> Database
```

```text
src/main/java/com/arenamanager/
├── config        # Demo data and application setup
├── controller
│   ├── rest      # JSON API controllers
│   └── web       # Thymeleaf page controllers
├── domain        # JPA entities
├── dto           # Request, response, form, route, and metric DTOs
├── exception     # Custom exceptions and global handler
├── mapper        # MapStruct mappers
├── repository    # Spring Data repositories
├── security      # JWT, form login, and route authorization
└── service       # Business rules, reports, audit logging
```

## Entity Relationships

| Relationship | Mapping | Purpose |
| --- | --- | --- |
| `Player` to `PlayerProfile` | One-to-one | Profile metadata such as hardware settings, grip style, and bio |
| `Team` to `Player` | One-to-many / many-to-one | Team roster ownership through `players.team_id` |
| `Tournament` to `Team` | Many-to-many | Registered squads through `tournament_teams` |
| `Tournament` to `BracketMatch` | One-to-many | Tournament bracket structure |
| `BracketMatch` to `Team` | Many-to-one | Home team, away team, and winner |
| `AuditLog` to `Tournament` and `BracketMatch` | Many-to-one | Operational history for bracket and score changes |

## Build Commands

Compile:

```powershell
.\mvnw.cmd compile
```

Test:

```powershell
.\mvnw.cmd test
```

Package:

```powershell
.\mvnw.cmd package
```

Skip tests while packaging:

```powershell
.\mvnw.cmd -DskipTests package
```

Run the generated JAR:

```powershell
java -jar target\arena-manager-0.0.1-SNAPSHOT.jar
```
