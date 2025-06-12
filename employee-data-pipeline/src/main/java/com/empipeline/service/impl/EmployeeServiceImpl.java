package com.empipeline.service.impl;

import com.empipeline.dto.EmployeeDTO;
import com.empipeline.model.Employee;
import com.empipeline.model.Department;
import com.empipeline.repository.EmployeeRepository;
import com.empipeline.repository.DepartmentRepository;
import com.empipeline.service.EmployeeService;
import com.empipeline.kafka.EmployeeEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeEventProducer employeeEventProducer;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new RuntimeException("Employee with email " + employeeDTO.getEmail() + " already exists");
        }

        Employee employee = new Employee();
        updateEmployeeFromDTO(employee, employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDTO savedDTO = convertToDTO(savedEmployee);
        
        // Send event to Kafka
        employeeEventProducer.sendEmployeeEvent(savedDTO, "EMPLOYEE_CREATED");
        
        return savedDTO;
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        updateEmployeeFromDTO(employee, employeeDTO);
        Employee updatedEmployee = employeeRepository.save(employee);
        EmployeeDTO updatedDTO = convertToDTO(updatedEmployee);
        
        // Send event to Kafka
        employeeEventProducer.sendEmployeeEvent(updatedDTO, "EMPLOYEE_UPDATED");
        
        return updatedDTO;
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        
        EmployeeDTO employeeDTO = convertToDTO(employee);
        employeeRepository.deleteById(id);
        
        // Send event to Kafka
        employeeEventProducer.sendEmployeeEvent(employeeDTO, "EMPLOYEE_DELETED");
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return convertToDTO(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));
        return convertToDTO(employee);
    }

    private void updateEmployeeFromDTO(Employee employee, EmployeeDTO dto) {
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setHireDate(dto.getHireDate());

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + dto.getDepartmentId()));
            employee.setDepartment(department);
        }
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPosition(employee.getPosition());
        dto.setSalary(employee.getSalary());
        dto.setHireDate(employee.getHireDate());
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setUpdatedAt(employee.getUpdatedAt());

        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
        }

        return dto;
    }
} 