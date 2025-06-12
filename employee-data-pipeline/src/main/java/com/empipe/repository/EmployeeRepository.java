package com.empipe.repository;

import com.empipe.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Optional<Employee> findByEmployeeId(String employeeId);
    
    List<Employee> findByDepartmentId(String departmentId);
    
    @Query("SELECT e FROM Employee e WHERE e.salary >= ?1")
    List<Employee> findBySalaryGreaterThanEqual(Double salary);
    
    @Query("SELECT e FROM Employee e WHERE e.hireDate >= ?1")
    List<Employee> findByHireDateAfter(java.time.LocalDateTime date);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.departmentId = ?1")
    long countByDepartment(String departmentId);
} 