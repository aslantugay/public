# Meridian Hub

Meridian Hub is a multi-tenant operations backend for mid-market companies. It
brings tenants, org units and members, a vendor/product catalog, procurement
(purchase orders, invoices, payments against a credit account), a document
workspace with a shared activity stream, and an automation layer (webhooks,
notification templates and rules).

Built with Spring Boot 3 (Java 17), Spring Data JPA and an in-memory H2 database
that is seeded on startup for local development.

## Modules

| Package | Responsibility |
|---------|----------------|
| `iam` | users, API keys, authentication, JWT, portable session context |
| `tenancy` | tenants, units, membership directory |
| `catalog` | vendors and products |
| `procurement` | purchase orders, invoices, payments, credit accounts |
| `docs` | documents, comments/activity feed, saved searches, sharing |
| `automation` | webhooks + delivery log, notification templates, rules, invites |
| `dataio` | XML / legacy / archive import |
| `platform` | request context, security, crypto, audit, configuration |

Each feature exposes a REST **API interface** (`*Api`) implemented by a thin
controller and served by a service (and, for the larger flows, a facade) so the
web layer stays decoupled from persistence and integrations.

## Build & run

```bash
mvn -DskipTests spring-boot:run
# or
mvn -DskipTests package && java -jar target/meridian-hub-1.4.0-SNAPSHOT.jar
```

The API listens on `http://localhost:8080`. In this environment identity is
provided by the edge gateway via `X-User-Id` / `X-Tenant-Id` / `X-Role` headers,
or by a bearer token issued at `POST /api/auth/login`.

## Selected endpoints

| Area | Endpoints |
|------|-----------|
| Auth | `POST /api/auth/login`, `POST /api/auth/reset-token`, `GET /api/auth/callback` |
| Tenancy | `GET/POST /api/tenants`, `GET /api/tenants/{id}`, units under `/api/tenants/{id}/units`, `DELETE /api/units/{id}` |
| IAM | `GET/PUT /api/users/{id}`, `/api/apikeys`, `/api/session/export|import` |
| Catalog | `/api/vendors`, `POST /api/vendors/{id}/logo`, `POST /api/products/import` |
| Procurement | `/api/invoices`, `GET /api/invoices/{id}/pdf`, `POST /api/invoices/{id}/pay`, `/api/purchase-orders` |
| Docs | `GET /api/documents/search`, `/api/documents/{id}`, `/api/documents/shared/{token}`, `/api/documents/filters` |
| Automation | `/api/webhooks`, `/api/notifications/templates`, `/api/automation/rules`, `/api/invites` |
| Import | `POST /api/import/tenant`, `/api/import/tenant/legacy`, `/api/import/attachments` |

## Seed data

Two tenants — **Acme Holding** and **Globex Corp** — each with units, members, a
credit account, documents, an invoice and sample automation activity.
