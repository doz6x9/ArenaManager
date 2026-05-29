# ArenaManager

ArenaManager is a Spring Boot 3, Java 21 backend for esports and LAN tournament operations. It gives organizers a secure dashboard for managing brackets and scores, while team captains and API consumers can retrieve tournament data through JWT-protected REST endpoints.

The project is designed to demonstrate a professional backend architecture: layered services, strict JPA relationships, DTO boundaries, generated MapStruct mappers, role-aware security, auditability, dashboard metrics, Docker packaging, and GitLab CI.

## Key Features

- Dual access model: REST API for captains and server-rendered Thymeleaf UI for organizers.
- Role-based security: organizer session login and JWT authentication for API clients.
- Tournament lifecycle: team registration, bracket generation, match score updates, and winner detection.
- Roster integrity: team capacity checks and player assignment validation.
- Audit trail: match score changes are recorded with actor, match, tournament, and change details.
- Operational dashboard: live counts for tournaments, teams, players, match statuses, and audit events.
- Reporting endpoints: dashboard and audit data exposed through JSON APIs.

## Tech Stack

- Java 21
- Spring Boot 3.3
- Spring Security
- Spring Data JPA
- Thymeleaf
- H2 in-memory database
- JJWT
- MapStruct
- Lombok
- Jakarta Validation
- OpenAPI 3 / Swagger UI
- Maven Wrapper
- Docker
- GitLab CI

## Architecture

```text
Controller -> Service -> Repository -> Database
```

```text
src/main/java/com/arenamanager/
├── controller
│   ├── rest      # JSON APIs for auth, teams, players, tournaments, matches, reports
│   └── web       # Thymeleaf organizer dashboard and bracket views
├── service       # Business logic, reporting, validation, audit creation
├── repository    # Spring Data JPA access layer
├── domain        # JPA entities and relational mappings
├── dto           # Request, response, form, metrics, and audit DTOs
├── mapper        # MapStruct entity/DTO mappers
├── security      # JWT service, filter, session login, RBAC rules
├── exception     # Custom exceptions and controller advice
└── config        # Demo seed data
```

## Entity Relationships

- `Player` to `PlayerProfile`: one-to-one. `player_profiles.player_id` is unique.
- `Team` to `Player`: one-to-many from team, many-to-one from player. `players.team_id` owns the foreign key.
- `Tournament` to `Team`: many-to-many through `tournament_teams`.
- `Tournament` to `BracketMatch`: one-to-many bracket structure.
- `BracketMatch` to `Team`: many-to-one for home, away, and winner teams.
- `AuditLog` to `Tournament` and `BracketMatch`: many-to-one audit context for score changes.

## API Highlights

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/auth/token` | Authenticate and receive a JWT |
| `GET` | `/api/tournaments/{id}/bracket` | Fetch a tournament bracket |
| `POST` | `/api/tournaments/{id}/bracket` | Generate first-round matches |
| `PUT` | `/api/matches/{id}/score` | Update a match score |
| `GET` | `/api/reports/dashboard` | Fetch dashboard metrics |
| `GET` | `/api/reports/audit` | Fetch recent audit events |

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

## Local Run

Java 21 is required, and `JAVA_HOME` should point to your JDK 21 install. The Maven wrapper downloads Maven automatically on first use, so a global Maven install is optional.

```powershell
.\mvnw.cmd spring-boot:run
```

Then open:

- Organizer dashboard: `http://localhost:8080/admin/dashboard`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- H2 console: `http://localhost:8080/h2-console`

Demo credentials:

- Organizer web login: `organizer` / `password`
- Captain/API login: `captain` / `password`

Get a JWT:

```bash
curl -X POST http://localhost:8080/api/auth/token \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"captain\",\"password\":\"password\"}"
```

Use the token with:

```text
Authorization: Bearer <token>
```

## Build and Test

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
docker build -t arenamanager .
```
