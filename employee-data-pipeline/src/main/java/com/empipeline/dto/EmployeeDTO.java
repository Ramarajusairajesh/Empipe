package com.empipeline.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private Long departmentId;
    private Double salary;
    private LocalDateTime hireDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 