#!/bin/bash

# Stop any running containers
echo "Stopping any running containers..."
docker-compose down

# Build the application
echo "Building the application..."
docker-compose build

# Start the services
echo "Starting the services..."
docker-compose up -d

# Wait for services to be ready
echo "Waiting for services to be ready..."
sleep 30

# Check service status
echo "Checking service status..."
docker-compose ps

echo "Application is running!"
echo "API is available at: http://localhost:8080"
echo "PostgreSQL is available at: localhost:5432"
echo "Kafka is available at: localhost:9092" 