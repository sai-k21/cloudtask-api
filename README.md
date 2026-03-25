# CloudTask API

A scalable, cloud-native Task Management REST API built with Spring Boot, PostgreSQL, and Docker — designed to handle high-volume workloads with horizontal scaling on AWS ECS Fargate.

## Features

- Full CRUD operations for task management
- Task filtering by status, priority, and assignee
- Overdue task detection
- Real-time metrics dashboard endpoint
- Centralized exception handling with consistent API responses
- Input validation on all endpoints
- Swagger/OpenAPI documentation
- Dockerized with Docker Compose for local development
- GitHub Actions CI/CD pipeline
- Production-ready for AWS ECS Fargate + RDS deployment

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

## Architecture

```
Client
  └── REST API (Spring Boot)
        ├── TaskController   → handles HTTP requests
        ├── TaskService      → business logic
        ├── TaskRepository   → database access (JPA)
        └── PostgreSQL       → persistent storage
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/tasks | Create a task |
| GET | /api/v1/tasks | Get all tasks |
| GET | /api/v1/tasks/{id} | Get task by ID |
| GET | /api/v1/tasks/status/{status} | Filter by status |
| GET | /api/v1/tasks/priority/{priority} | Filter by priority |
| GET | /api/v1/tasks/assignee/{name} | Filter by assignee |
| GET | /api/v1/tasks/overdue | Get overdue tasks |
| GET | /api/v1/tasks/metrics | Get metrics dashboard |
| PUT | /api/v1/tasks/{id} | Update a task |
| PATCH | /api/v1/tasks/{id}/status | Update status only |
| DELETE | /api/v1/tasks/{id} | Delete a task |

**Task Status values:** `TODO`, `IN_PROGRESS`, `IN_REVIEW`, `DONE`, `CANCELLED`

**Priority values:** `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`

## Running Locally

### Prerequisites
- Java 17+
- Maven 3.9+
- Docker and Docker Compose

### Option 1: Docker Compose (Recommended)

```bash
git clone https://github.com/saikumar-moguluri/cloudtask-api.git
cd cloudtask-api
docker-compose up --build
```

API will be available at: `http://localhost:8080`

### Option 2: Run with local PostgreSQL

```bash
# Start PostgreSQL
createdb cloudtask

# Run the app
mvn spring-boot:run
```

## API Documentation

Once running, visit:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`
- Health check: `http://localhost:8080/actuator/health`

## Example Request

```bash
# Create a task
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

# Get metrics
curl http://localhost:8080/api/v1/tasks/metrics
```

## Example Response

```json
{
  "success": true,
  "message": "Task created successfully",
  "data": {
    "id": 1,
    "title": "Implement user authentication",
    "status": "TODO",
    "priority": "HIGH",
    "assignedTo": "sai@example.com",
    "createdAt": "2026-03-25T10:00:00"
  },
  "timestamp": "2026-03-25T10:00:00"
}
```

## Cloud Deployment (AWS)

This service is designed for deployment on AWS ECS Fargate:

- **Container Registry:** Amazon ECR
- **Orchestration:** ECS Fargate (stateless, auto-scaling)
- **Database:** Amazon RDS PostgreSQL (private subnet)
- **Load Balancer:** Application Load Balancer
- **Secrets:** AWS Secrets Manager (no hardcoded credentials)
- **Monitoring:** Amazon CloudWatch logs and alarms

## Author

**Sai Kumar Moguluri**
- LinkedIn: [linkedin.com/in/sai-1899k](https://linkedin.com/in/sai-1899k)
- Email: mogulurisaikumar@gmail.com
