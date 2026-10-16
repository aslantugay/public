# Helix Portal

Backend service for the Helix clinic management portal. Provides REST APIs for
staff authentication, patient records, invoicing, clinical notes, document
management and third-party integrations.

## Tech stack

- Java 17, Spring Boot 3.2.x
- Spring MVC, Spring Data JPA, Spring Security
- H2 (in-memory) for local/dev; swap the datasource for production
- FreeMarker for server-side report templates

## Modules

| Package            | Responsibility                                        |
|--------------------|-------------------------------------------------------|
| `auth`             | Login, registration, password reset, JWT sessions     |
| `user`             | Staff profiles and directory                          |
| `patient`          | Patient records, search, saved filters, aggregates    |
| `note`             | Clinical notes and analytics                          |
| `invoice`          | Billing, revenue reports, PDF export                  |
| `document`         | Uploads, sharing, imports (XML/ZIP)                   |
| `integration`      | Outbound fetch, templating, directory (LDAP) lookups  |

## Build & run

```bash
mvn clean package
java -jar target/helix-portal-0.0.1-SNAPSHOT.jar
```

The API starts on `http://localhost:8080`. A small demo dataset is seeded on
startup. The H2 console is available at `/h2-console` in dev profiles.

## API overview

- `POST /api/auth/login` — obtain a session token
- `POST /api/auth/register` — create a staff account
- `GET  /api/patients/search?name=` — search patients
- `GET  /api/invoices/{id}` — invoice detail
- `POST /api/documents/upload` — upload a document
- `GET  /api/documents/shared/{token}` — open a shared document

See the controller classes for the full route list.

## License

Internal project — all rights reserved.
