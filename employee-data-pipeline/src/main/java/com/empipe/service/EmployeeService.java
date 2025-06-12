package com.empipe.service;

import com.empipe.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    
    Employee saveEmployee(Employee employee);
    
    List<Employee> saveEmployees(List<Employee> employees);
    
    Optional<Employee> getEmployeeById(Long id);
    
    Optional<Employee> getEmployeeByEmployeeId(String employeeId);
    
    List<Employee> getAllEmployees();
    
    List<Employee> getEmployeesByDepartment(String departmentId);
    
    Employee updateEmployee(Employee employee);
    
    void deleteEmployee(Long id);
    
    boolean existsByEmail(String email);
    
    long countByDepartment(String departmentId);
    
    List<Employee> getEmployeesBySalaryGreaterThanEqual(Double salary);
    
    List<Employee> getEmployeesHiredAfter(java.time.LocalDateTime date);
} 