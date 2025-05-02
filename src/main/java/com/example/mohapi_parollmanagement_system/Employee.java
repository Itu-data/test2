package com.example.mohapi_parollmanagement_system;

import javafx.beans.property.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Employee {

    private final IntegerProperty employeeId;
    private final StringProperty name;
    private final StringProperty position;
    private final StringProperty department;
    private final DoubleProperty basicSalary;
    private final DoubleProperty workingHours;
    private final DoubleProperty deductions;

    // Constructor with all parameters
    public Employee(int employeeId, String name, String position, String department, double basicSalary, double workingHours, double deductions) {
        this.employeeId = new SimpleIntegerProperty(employeeId);
        this.name = new SimpleStringProperty(name);
        this.position = new SimpleStringProperty(position);
        this.department = new SimpleStringProperty(department);
        this.basicSalary = new SimpleDoubleProperty(basicSalary);
        this.workingHours = new SimpleDoubleProperty(workingHours);
        this.deductions = new SimpleDoubleProperty(deductions);
    }

    // Empty constructor for creating blank employee objects
    public Employee() {
        this(0, "", "", "", 0.0, 0.0, 0.0);  // Default values
    }

    // Constructor with only basic info (used in the PayrollController)
    public Employee(int employeeId, String name, String position, String department) {
        this(employeeId, name, position, department, 0.0, 0.0, 0.0);  // Default basic salary, working hours, and deductions
    }

    // Getters and Property methods for JavaFX bindings
    public int getEmployeeId() {
        return employeeId.get();
    }

    public IntegerProperty employeeIdProperty() {
        return employeeId;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public String getDepartment() {
        return department.get();
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public double getBasicSalary() {
        return basicSalary.get();
    }

    public DoubleProperty basicSalaryProperty() {
        return basicSalary;
    }

    public double getWorkingHours() {
        return workingHours.get();
    }

    public DoubleProperty workingHoursProperty() {
        return workingHours;
    }

    public double getDeductions() {
        return deductions.get();
    }

    public DoubleProperty deductionsProperty() {
        return deductions;
    }

    // Method to insert employee data into the database
    public void saveEmployeeToDatabase() throws SQLException {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/payroll_management"; // Your database URL
        String user = "root";  // Your MySQL username
        String password = "901017297"; // Your MySQL password

        // Establish a connection
        Connection connection = DriverManager.getConnection(url, user, password);

        // SQL query to insert data into employee table
        String query = "INSERT INTO employees (name, position, department, basic_salary, working_hours, deductions) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set parameters for the PreparedStatement
            preparedStatement.setString(1, getName());
            preparedStatement.setString(2, getPosition());
            preparedStatement.setString(3, getDepartment());
            preparedStatement.setDouble(4, getBasicSalary());
            preparedStatement.setDouble(5, getWorkingHours());
            preparedStatement.setDouble(6, getDeductions());

            // Execute the query
            preparedStatement.executeUpdate();
            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting employee data", e);
        } finally {
            // Close the connection (this should ideally be done in a finally block)
            if (connection != null) {
                connection.close();
            }
        }
    }
}
