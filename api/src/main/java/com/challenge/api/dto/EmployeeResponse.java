package com.challenge.api.dto;

import com.challenge.api.model.Employee;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Employee Response Class
 */
public class EmployeeResponse {

    // ================
    // EMPLOYEE FIELDS
    // =================

    /**
     * Employee UUID
     */
    private UUID uuid;
    /**
     * Employee full name
     */
    private String fullName;
    /**
     * Employee salary
     */
    private Integer salary;
    /**
     * Employee age
     */
    private Integer age;
    /**
     * Employee job title
     */
    private String jobTitle;
    /**
     * Employee email
     */
    private String email;
    /**
     * Employee hire date
     */
    private String hireDate;
    /**
     * Employee termination date
     */
    private String terminationDate;
    /**
     * Employee is current
     */
    private boolean isActive;

    /**
     * Constructor
     *
     * @param employee
     */
    public EmployeeResponse(Employee employee) {
        this.uuid = employee.getUuid();
        this.fullName = employee.getFullName();
        this.email = employee.getEmail();
        this.jobTitle = employee.getJobTitle();
        this.age = employee.getAge();
        this.salary = employee.getSalary();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (employee.getContractHireDate() != null) {
            LocalDateTime hireDateTime =
                    LocalDateTime.ofInstant(employee.getContractHireDate(), ZoneId.systemDefault());
            this.hireDate = hireDateTime.format(formatter);
        }

        if (employee.getContractTerminationDate() != null) {
            LocalDateTime terminationDateTime =
                    LocalDateTime.ofInstant(employee.getContractTerminationDate(), ZoneId.systemDefault());
            this.terminationDate = terminationDateTime.format(formatter);
            this.isActive = false;
        } else {
            this.isActive = true;
        }
    }

    // Getters
    public UUID getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getHireDate() {
        return hireDate;
    }

    public String getTerminationDate() {
        return terminationDate;
    }

    public boolean isActive() {
        return isActive;
    }
}
