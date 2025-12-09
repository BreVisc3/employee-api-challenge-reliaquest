package com.challenge.api.service.impl;

import com.challenge.api.model.Employee;
import com.challenge.api.model.impl.EmployeeImpl;
import com.challenge.api.service.EmployeeService;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Employee Service interface implementation
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * Map of Employees
     */
    private final Map<UUID, Employee> employeeStore = new ConcurrentHashMap<>();

    /**
     * Constructor
     */
    public EmployeeServiceImpl() {
        initializeMockData();
    }

    /**
     * Populate mock data
     */
    private void initializeMockData() {
        // Create first employee
        Employee emp1 = new EmployeeImpl();
        emp1.setUuid(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        emp1.setFirstName("John");
        emp1.setLastName("Smith");
        emp1.setSalary(75000);
        emp1.setAge(35);
        emp1.setJobTitle("Software Engineer");
        emp1.setEmail("john.smith@example.com");
        emp1.setContractHireDate(Instant.parse("2020-01-15T00:00:00Z"));

        // Create second employee
        Employee emp2 = new EmployeeImpl();
        emp2.setUuid(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        emp2.setFirstName("Jane");
        emp2.setLastName("Doe");
        emp2.setSalary(85000);
        emp2.setAge(28);
        emp2.setJobTitle("Product Manager");
        emp2.setEmail("jane.doe@example.com");
        emp2.setContractHireDate(Instant.parse("2019-06-01T00:00:00Z"));

        // Create third employee (terminated)
        Employee emp3 = new EmployeeImpl();
        emp3.setUuid(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));
        emp3.setFirstName("Bob");
        emp3.setLastName("Johnson");
        emp3.setSalary(65000);
        emp3.setAge(42);
        emp3.setJobTitle("QA Engineer");
        emp3.setEmail("bob.johnson@example.com");
        emp3.setContractHireDate(Instant.parse("2018-03-10T00:00:00Z"));
        emp3.setContractTerminationDate(Instant.parse("2022-12-31T00:00:00Z"));

        employeeStore.put(emp1.getUuid(), emp1);
        employeeStore.put(emp2.getUuid(), emp2);
        employeeStore.put(emp3.getUuid(), emp3);
    }

    /**
     * Get all employees in the system
     *
     * @return List of employees
     */
    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStore.values());
    }

    /**
     * Get an employee by their UUID
     *
     * @param uuid
     * @return employee with UUID or NULL
     */
    @Override
    public Employee getEmployeeByUuid(UUID uuid) {
        Employee employee = employeeStore.get(uuid);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with UUID: " + uuid);
        }
        return employee;
    }

    /**
     * Create new employee in system
     *
     * @param employee to add
     * @return Employee added to system
     */
    @Override
    public Employee createEmployee(Employee employee) {
        // Validate required fields
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }

        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        // Generate UUID if not provided
        if (employee.getUuid() == null) {
            employee.setUuid(UUID.randomUUID());
        }

        // Set hire date if not provided
        if (employee.getContractHireDate() == null) {
            employee.setContractHireDate(Instant.now());
        }

        // Check if UUID already exists
        if (employeeStore.containsKey(employee.getUuid())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Employee with UUID " + employee.getUuid() + " already exists");
        }

        // Check if email already exists
        boolean emailExists =
                employeeStore.values().stream().anyMatch(e -> e.getEmail().equalsIgnoreCase(employee.getEmail()));

        if (emailExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Employee with email " + employee.getEmail() + " already exists");
        }

        // Save employee
        employeeStore.put(employee.getUuid(), employee);

        return employee;
    }

    // Additional helper methods

    /**
     * Get list of current employees
     * @return list
     */
    public List<Employee> getActiveEmployees() {
        return employeeStore.values().stream()
                .filter(e -> e.getContractTerminationDate() == null)
                .toList();
    }

    /**
     * Get list of employees with specific title
     *
     * @param jobTitle
     * @return list
     */
    public List<Employee> getEmployeesByJobTitle(String jobTitle) {
        return employeeStore.values().stream()
                .filter(e -> e.getJobTitle() != null && e.getJobTitle().equalsIgnoreCase(jobTitle))
                .toList();
    }
}
