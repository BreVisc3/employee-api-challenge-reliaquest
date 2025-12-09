package com.challenge.api.model.impl;

import com.challenge.api.model.Employee;
import java.time.Instant;
import java.util.UUID;

/**
 * Every abstraction of an Employee should, at the bare minimum, implement this interface. Consider this a binding
 * contract for the domain model of an Employee.
 */
public class EmployeeImpl implements Employee {

    // ================
    // EMPLOYEE FIELDS
    // =================

    /**
     * Employee UUID
     */
    private UUID uuid;
    /**
     * Employee first name
     */
    private String firstName;
    /**
     * Employee last name
     */
    private String lastName;
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
    private Instant contractHireDate;
    /**
     * Employee termination date
     */
    private Instant contractTerminationDate;

    // =================
    // CONSTRUCTORS
    // =================

    /**
     * Default Constructor
     */
    public EmployeeImpl() {}

    /**
     * Paramaterized Constructor
     *
     * @param uuid
     * @param firstName
     * @param lastName
     * @param fullName
     * @param salary
     * @param age
     * @param jobTitle
     * @param email
     * @param contractHireDate
     * @param contractTerminationDate
     */
    public EmployeeImpl(
            UUID uuid,
            String firstName,
            String lastName,
            Integer salary,
            Integer age,
            String jobTitle,
            String email,
            Instant contractHireDate,
            Instant contractTerminationDate) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.age = age;
        this.jobTitle = jobTitle;
        this.email = email;
        this.contractHireDate = contractHireDate;
        this.contractTerminationDate = contractTerminationDate;
        updateFullName();
    }

    // =================
    // GETTERS/SETTERS
    // =================

    // ========== GETTERS ==========

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getFullName() {
        if (fullName == null || fullName.isEmpty()) {
            updateFullName();
        }
        return fullName;
    }

    @Override
    public Integer getSalary() {
        return salary;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Instant getContractHireDate() {
        return contractHireDate;
    }

    /**
     * Nullable.
     * @return null, if Employee has not been terminated.
     */
    @Override
    public Instant getContractTerminationDate() {
        return contractTerminationDate;
    }

    // ========== SETTERS ==========

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateFullName();
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateFullName();
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setContractHireDate(Instant contractHireDate) {
        this.contractHireDate = contractHireDate;
    }

    @Override
    public void setContractTerminationDate(Instant contractTerminationDate) {
        this.contractTerminationDate = contractTerminationDate;
    }

    // ===============
    // HELPER METHODS
    // ===============

    /**
     * Determines if the employee is currently active.
     * An employee is considered active if they have no termination date.
     */
    public boolean isActive() {
        return contractTerminationDate == null;
    }

    /**
     * Terminates the employee's contract effective immediately.
     */
    public void terminateContract() {
        this.contractTerminationDate = Instant.now();
    }

    /**
     * Terminates the employee's contract with a specific date.
     * @param terminationDate The date when the contract ends
     */
    public void terminateContract(Instant terminationDate) {
        this.contractTerminationDate = terminationDate;
    }

    private void updateFullName() {
        if (firstName != null && lastName != null) {
            this.fullName = firstName + " " + lastName;
        } else if (firstName != null) {
            this.fullName = firstName;
        } else if (lastName != null) {
            this.fullName = lastName;
        }
    }

    @Override
    public String toString() {
        return "EmployeeImpl{" + "uuid="
                + uuid + ", firstName='"
                + firstName + '\'' + ", lastName='"
                + lastName + '\'' + ", fullName='"
                + getFullName() + '\'' + ", salary="
                + salary + ", age="
                + age + ", jobTitle='"
                + jobTitle + '\'' + ", email='"
                + email + '\'' + ", contractHireDate="
                + contractHireDate + ", contractTerminationDate="
                + contractTerminationDate + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeImpl employee = (EmployeeImpl) o;
        return uuid.equals(employee.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
