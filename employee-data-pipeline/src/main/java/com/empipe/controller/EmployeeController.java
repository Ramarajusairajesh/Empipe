package com.empipe.controller;

import com.empipe.model.Employee;
import com.empipe.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        log.debug("Creating new employee: {}", employee);
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Employee>> createEmployees(@RequestBody List<Employee> employees) {
        log.debug("Creating {} new employees", employees.size());
        return ResponseEntity.ok(employeeService.saveEmployees(employees));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        log.debug("Fetching employee with id: {}", id);
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employee-id/{employeeId}")
    public ResponseEntity<Employee> getEmployeeByEmployeeId(@PathVariable String employeeId) {
        log.debug("Fetching employee with employeeId: {}", employeeId);
        return employeeService.getEmployeeByEmployeeId(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.debug("Fetching all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String departmentId) {
        log.debug("Fetching employees in department: {}", departmentId);
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        log.debug("Updating employee with id: {}", id);
        return employeeService.getEmployeeById(id)
                .map(existingEmployee -> {
                    employee.setId(id);
                    return ResponseEntity.ok(employeeService.updateEmployee(employee));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("Deleting employee with id: {}", id);
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employeeService.deleteEmployee(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/salary/{minSalary}")
    public ResponseEntity<List<Employee>> getEmployeesByMinSalary(@PathVariable Double minSalary) {
        log.debug("Fetching employees with minimum salary: {}", minSalary);
        return ResponseEntity.ok(employeeService.getEmployeesBySalaryGreaterThanEqual(minSalary));
    }
} 