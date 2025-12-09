package com.challenge.api.controller;

import com.challenge.api.model.Employee;
import com.challenge.api.model.impl.EmployeeImpl;
import com.challenge.api.service.EmployeeService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Employee Controller Class
 */
@Controller
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Constructor
     *
     * @param employeeService
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Get All Employees in system
     */
    @GetMapping(produces = "text/html")
    public String getAllEmployeesHtml(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("title", "Employee Directory");
        return "employees"; // This will look for employees.html
    }

    /**
     * JSON API - original endpoint (for API clients)
     */
    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Employee> getAllEmployeesJson() {
        return employeeService.getAllEmployees();
    }

    /**
     * Get employee by their UUID (as extension in path)
     *
     * @param uuid
     * @return
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Employee> getEmployeeByUuid(@PathVariable UUID uuid) {
        Employee employee = employeeService.getEmployeeByUuid(uuid);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    /**
     * Get uuid HTML
     *
     * @param uuid
     * @param model
     * @return
     */
    @GetMapping(value = "/{uuid}", produces = "text/html")
    public String getEmployeeByUuidHtml(@PathVariable UUID uuid, Model model) {
        Employee employee = employeeService.getEmployeeByUuid(uuid);
        model.addAttribute("employee", employee);
        return "employee-detail";
    }

    /**
     * Get uuid JSON
     *
     * @param uuid
     * @return
     */
    @GetMapping(value = "/{uuid}", produces = "application/json")
    @ResponseBody
    public Employee getEmployeeByUuidJson(@PathVariable UUID uuid) {
        return employeeService.getEmployeeByUuid(uuid);
    }

    /**
     * Create new employee in system
     *
     * @param request
     * @return
     */
    @PostMapping(produces = "application/json")
    @ResponseBody
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest request) {
        // Add null check
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        // Convert EmployeeRequest to Employee
        Employee employee = convertToEmployee(request);

        // Call service
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    /**
     * Create employee form
     *
     * @return
     */
    @GetMapping("/create")
    public String showCreateEmployeeForm() {
        return "create-employee";
    }

    /**
     * Create employee JSON
     *
     * @param request
     * @return
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Employee> createEmployeeJson(@RequestBody EmployeeRequest request) {
        Employee employee = convertToEmployee(request);
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    /**
     * Form submission endpoint
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public String createEmployeeForm(@ModelAttribute EmployeeRequest request, Model model) {
        try {
            Employee employee = convertToEmployee(request);
            Employee createdEmployee = employeeService.createEmployee(employee);
            model.addAttribute("employee", createdEmployee);
            model.addAttribute("success", true);
            return "employee-detail"; // Show the newly created employee
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "create-employee"; // Return to form with error
        }
    }

    /**
     * Error handling endpoint
     */
    @GetMapping("/**")
    public String handleNotFound() {
        return "not-found";
    }

    /**
     * Helper method - creates concrete EmployeeImpl from DTO
     * @param request
     * @return
     */
    Employee convertToEmployee(EmployeeRequest request) {
        EmployeeImpl employee = new EmployeeImpl();

        // Required fields
        employee.setFirstName(request.getFirstName());
        employee.setEmail(request.getEmail());

        // Optional fields
        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }
        if (request.getJobTitle() != null) {
            employee.setJobTitle(request.getJobTitle());
        }
        if (request.getSalary() != null) {
            employee.setSalary(request.getSalary());
        }
        if (request.getAge() != null) {
            employee.setAge(request.getAge());
        }
        if (request.getContractHireDate() != null) {
            employee.setContractHireDate(request.getContractHireDate());
        }

        return employee;
    }

    // ============================
    // EMPLOYEE REQUEST PRIV CLASS
    // ============================

    public static class EmployeeRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String jobTitle;
        private Integer salary;
        private Integer age;
        private Instant contractHireDate;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public Integer getSalary() {
            return salary;
        }

        public void setSalary(Integer salary) {
            this.salary = salary;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Instant getContractHireDate() {
            return contractHireDate;
        }

        public void setContractHireDate(Instant contractHireDate) {
            this.contractHireDate = contractHireDate;
        }
    }
}
