package com.empipeline.service;

import com.empipeline.dto.DepartmentDTO;
import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);
    void deleteDepartment(Long id);
    DepartmentDTO getDepartment(Long id);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO getDepartmentByName(String name);
} 