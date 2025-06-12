package com.empipe.service.impl;

import com.empipe.model.Employee;
import com.empipe.repository.EmployeeRepository;
import com.empipe.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        log.debug("Saving employee: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public List<Employee> saveEmployees(List<Employee> employees) {
        log.debug("Saving {} employees", employees.size());
        return employeeRepository.saveAll(employees);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(Long id) {
        log.debug("Fetching employee by id: {}", id);
        return employeeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeByEmployeeId(String employeeId) {
        log.debug("Fetching employee by employeeId: {}", employeeId);
        return employeeRepository.findByEmployeeId(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        log.debug("Fetching all employees");
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDepartment(String departmentId) {
        log.debug("Fetching employees by department: {}", departmentId);
        return employeeRepository.findByDepartmentId(departmentId);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        log.debug("Updating employee: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        log.debug("Deleting employee with id: {}", id);
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        log.debug("Checking if employee exists with email: {}", email);
        return employeeRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByDepartment(String departmentId) {
        log.debug("Counting employees in department: {}", departmentId);
        return employeeRepository.countByDepartment(departmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesBySalaryGreaterThanEqual(Double salary) {
        log.debug("Fetching employees with salary >= {}", salary);
        return employeeRepository.findBySalaryGreaterThanEqual(salary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesHiredAfter(LocalDateTime date) {
        log.debug("Fetching employees hired after: {}", date);
        return employeeRepository.findByHireDateAfter(date);
    }
} 