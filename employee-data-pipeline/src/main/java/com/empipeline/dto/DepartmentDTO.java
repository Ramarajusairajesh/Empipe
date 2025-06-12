package com.empipeline.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 