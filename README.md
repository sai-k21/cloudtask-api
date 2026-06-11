# CloudTask API

Scalable cloud-native task management REST API built with Spring Boot, PostgreSQL, and Docker. Designed for high-volume workloads with horizontal scaling on AWS ECS Fargate.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Database | PostgreSQL 15 |
| ORM | Spring Data JPA / Hibernate |
| Containerization | Docker, Docker Compose |
| CI/CD | GitHub Actions |
| API Docs | Swagger UI / OpenAPI 3 |
| Cloud | AWS ECS Fargate, RDS, ECR, CloudWatch |

## Features

- Full CRUD for task management with status and priority tracking
- Task filtering by status, priority, and assignee
- Overdue task detection
- Real-time metrics dashboard endpoint
- Centralized exception handling with consistent API responses
- Input validation on all endpoints
- Production-ready for AWS ECS Fargate + RDS deployment

## Architecture

```
Client
  └── REST API (Spring Boot)
        ├── TaskController   — handles HTTP requests
        ├── TaskService      — business logic
        ├── TaskRepository   — database access (JPA)
        └── PostgreSQL       — persistent storage
```

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/v1/tasks | Create a task |
| GET | /api/v1/tasks | Get all tasks |
| GET | /api/v1/tasks/{id} | Get task by ID |
| GET | /api/v1/tasks/status/{status} | Filter by status |
| GET | /api/v1/tasks/priority/{priority} | Filter by priority |
| GET | /api/v1/tasks/assignee/{name} | Filter by assignee |
| GET | /api/v1/tasks/overdue | Get overdue tasks |
| GET | /api/v1/tasks/metrics | Metrics dashboard |
| PUT | /api/v1/tasks/{id} | Update a task |
| PATCH | /api/v1/tasks/{id}/status | Update status only |
| DELETE | /api/v1/tasks/{id} | Delete a task |

**Status values:** TODO, IN_PROGRESS, IN_REVIEW, DONE, CANCELLED
**Priority values:** LOW, MEDIUM, HIGH, CRITICAL

## Getting Started

**Prerequisites:** Java 17+, Maven 3.9+, Docker

```bash
git clone https://github.com/sai-k21/cloudtask-api.git
cd cloudtask-api
docker-compose up --build
```

API available at: `http://localhost:8080`
Swagger UI: `http://localhost:8080/swagger-ui.html`
Health check: `http://localhost:8080/actuator/health`

## Example Request

```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Implement user authentication",
    "description": "Add JWT-based auth to all protected endpoints",
    "status": "TODO",
    "priority": "HIGH",
    "assignedTo": "sai@example.com",
    "dueDate": "2026-04-01T09:00:00"
  }'
```

## Cloud Deployment

Designed for AWS ECS Fargate:

- Container registry via Amazon ECR
- Stateless containers with auto-scaling on ECS Fargate
- Database on Amazon RDS PostgreSQL in a private subnet
- Secrets managed via AWS Secrets Manager
- Monitoring via Amazon CloudWatch

## Author

**Sai Kumar Moguluri**
GitHub: [github.com/sai-k21](https://github.com/sai-k21)
