package com.challenge.api.service;

import com.challenge.api.model.Employee;
import java.util.List;
import java.util.UUID;

/**
 * Employee Service interface
 */
public interface EmployeeService {

    /**
     * Get all employees in system
     *
     * @return list of all employees
     */
    List<Employee> getAllEmployees();

    /**
     * Get employee by their UUID
     *
     * @param uuid
     * @return Employee with UUID or NULL
     */
    Employee getEmployeeByUuid(UUID uuid);

    /**
     * Create new employee in system
     *
     * @param employee
     * @return Employee added
     */
    Employee createEmployee(Employee employee);
}
