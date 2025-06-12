package com.empipeline.model;

import java.time.LocalDateTime;

public class EmployeeEvent {
    private Long id;
    private String eventType;
    private Employee employee;
    private LocalDateTime timestamp;

    public EmployeeEvent() {
    }

    public EmployeeEvent(Long id, String eventType, Employee employee, LocalDateTime timestamp) {
        this.id = id;
        this.eventType = eventType;
        this.employee = employee;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 