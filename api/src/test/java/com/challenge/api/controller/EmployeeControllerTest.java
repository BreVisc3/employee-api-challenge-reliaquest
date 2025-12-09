package com.challenge.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.challenge.api.model.Employee;
import com.challenge.api.service.EmployeeService;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void testGetAllEmployeesJson() {
        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

        // Test JSON endpoint
        List<Employee> response = employeeController.getAllEmployeesJson();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void testGetEmployeeByUuid() {
        UUID uuid = UUID.randomUUID();
        Employee mockEmployee = mock(Employee.class);
        when(employeeService.getEmployeeByUuid(uuid)).thenReturn(mockEmployee);

        ResponseEntity<Employee> response = employeeController.getEmployeeByUuid(uuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockEmployee, response.getBody());
    }

    @Test
    void testCreateEmployee() {
        EmployeeController.EmployeeRequest request = new EmployeeController.EmployeeRequest();
        request.setFirstName("Test");
        request.setEmail("test@example.com");

        Employee mockEmployee = mock(Employee.class);
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(mockEmployee);

        ResponseEntity<Employee> response = employeeController.createEmployee(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(employeeService, times(1)).createEmployee(any(Employee.class));
    }
}
