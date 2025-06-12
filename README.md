# Employee Data Pipeline Manager (Empipe)

A robust data pipeline solution for processing and managing employee data from multiple sources.

## Features

- Process 15,000+ records from multiple data sources
- Real-time data processing using Apache Kafka
- Advanced batch optimization for improved performance
- 95% data accuracy rate
- 60% reduction in processing time
- Secure data storage in PostgreSQL

## Technology Stack

- Java 17
- Spring Boot 3.x
- Apache Kafka
- PostgreSQL
- Docker & Docker Compose

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven

## Getting Started

1. Clone the repository
2. Start the required services:
   ```bash
   docker-compose up -d
   ```
3. Build the application:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Architecture

The application follows a microservices architecture with the following components:

- Data Ingestion Service
- Data Processing Service
- Data Storage Service
- Data Validation Service

## Configuration

The application can be configured through `application.yml` files in the respective services.

## License

MIT License 
