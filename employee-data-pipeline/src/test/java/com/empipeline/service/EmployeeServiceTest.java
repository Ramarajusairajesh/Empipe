package com.empipeline.service;

import com.empipeline.dto.EmployeeDTO;
import com.empipeline.model.Employee;
import com.empipeline.repository.EmployeeRepository;
import com.empipeline.service.impl.EmployeeServiceImpl;
import com.empipeline.kafka.EmployeeEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeEventProducer employeeEventProducer;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setPosition("Software Engineer");
        employeeDTO.setSalary(100000.0);
        employeeDTO.setHireDate(LocalDateTime.now());
    }

    @Test
    void createEmployee_Success() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        when(employeeRepository.existsByEmail(anyString())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Act
        EmployeeDTO result = employeeService.createEmployee(employeeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(employeeRepository).save(any(Employee.class));
        verify(employeeEventProducer).sendEmployeeEvent(any(EmployeeDTO.class), eq("EMPLOYEE_CREATED"));
    }

    @Test
    void createEmployee_DuplicateEmail() {
        // Arrange
        when(employeeRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> employeeService.createEmployee(employeeDTO));
        verify(employeeRepository, never()).save(any(Employee.class));
        verify(employeeEventProducer, never()).sendEmployeeEvent(any(EmployeeDTO.class), anyString());
    }

    @Test
    void getEmployee_Success() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Act
        EmployeeDTO result = employeeService.getEmployee(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getEmployee_NotFound() {
        // Arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> employeeService.getEmployee(1L));
    }
} 