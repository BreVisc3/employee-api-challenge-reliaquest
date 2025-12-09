package com.challenge.api.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.challenge.api.model.Employee;
import com.challenge.api.model.impl.EmployeeImpl;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceImpl();
    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployees() {
        // Act
        List<Employee> employees = employeeService.getAllEmployees();

        // Assert
        assertNotNull(employees);
        assertTrue(employees.size() >= 3); // Should have at least the mock data
    }

    @Test
    void getEmployeeByUuid_WithValidUuid_ShouldReturnEmployee() {
        // Arrange
        UUID existingUuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        // Act
        Employee employee = employeeService.getEmployeeByUuid(existingUuid);

        // Assert
        assertNotNull(employee);
        assertEquals(existingUuid, employee.getUuid());
        assertEquals("John", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
    }

    @Test
    void getEmployeeByUuid_WithInvalidUuid_ShouldThrowException() {
        // Arrange
        UUID invalidUuid = UUID.randomUUID();

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            employeeService.getEmployeeByUuid(invalidUuid);
        });
    }

    @Test
    void createEmployee_WithValidData_ShouldCreateEmployee() {
        // Arrange
        Employee newEmployee = new EmployeeImpl();
        newEmployee.setFirstName("Alice");
        newEmployee.setLastName("Johnson");
        newEmployee.setEmail("alice.johnson@example.com");
        newEmployee.setJobTitle("Engineer");
        newEmployee.setSalary(80000);
        newEmployee.setAge(30);
        newEmployee.setContractHireDate(Instant.now());

        // Act
        Employee createdEmployee = employeeService.createEmployee(newEmployee);

        // Assert
        assertNotNull(createdEmployee);
        assertNotNull(createdEmployee.getUuid());
        assertEquals("Alice", createdEmployee.getFirstName());
        assertEquals("alice.johnson@example.com", createdEmployee.getEmail());

        // Verify employee was added to store
        List<Employee> allEmployees = employeeService.getAllEmployees();
        assertTrue(allEmployees.contains(createdEmployee));
    }

    @Test
    void createEmployee_WithoutFirstName_ShouldThrowException() {
        // Arrange
        Employee invalidEmployee = new EmployeeImpl();
        invalidEmployee.setEmail("test@example.com");

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            employeeService.createEmployee(invalidEmployee);
        });
    }

    @Test
    void createEmployee_WithoutEmail_ShouldThrowException() {
        // Arrange
        Employee invalidEmployee = new EmployeeImpl();
        invalidEmployee.setFirstName("Test");

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            employeeService.createEmployee(invalidEmployee);
        });
    }

    @Test
    void createEmployee_WithDuplicateEmail_ShouldThrowException() {
        // Arrange
        Employee employee1 = new EmployeeImpl();
        employee1.setFirstName("Test1");
        employee1.setEmail("duplicate@example.com");
        employeeService.createEmployee(employee1);

        Employee employee2 = new EmployeeImpl();
        employee2.setFirstName("Test2");
        employee2.setEmail("duplicate@example.com"); // Same email

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            employeeService.createEmployee(employee2);
        });
    }

    @Test
    void getActiveEmployees_ShouldReturnOnlyActiveEmployees() {
        // Act
        List<Employee> activeEmployees = employeeService.getActiveEmployees();

        // Assert
        assertNotNull(activeEmployees);
        activeEmployees.forEach(employee -> {
            assertNull(employee.getContractTerminationDate());
        });
    }

    @Test
    void getEmployeesByJobTitle_WithExistingTitle_ShouldReturnEmployees() {
        // Act
        List<Employee> softwareEngineers = employeeService.getEmployeesByJobTitle("Software Engineer");

        // Assert
        assertNotNull(softwareEngineers);
        softwareEngineers.forEach(employee -> {
            assertEquals("Software Engineer", employee.getJobTitle());
        });
    }
}
