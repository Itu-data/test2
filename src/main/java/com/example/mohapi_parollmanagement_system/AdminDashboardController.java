package com.example.mohapi_parollmanagement_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class AdminDashboardController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnManageEmployees;

    @FXML
    private Button btnLogout;

    @FXML
    private TableView<Employee> employeeTable; // TableView for displaying employees

    @FXML
    private TableColumn<Employee, String> colName;

    @FXML
    private TableColumn<Employee, String> colRole;

    @FXML
    private TableColumn<Employee, Double> colSalary;

    // Method to handle loading employee data into the table
    public void loadEmployeeData() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT * FROM employees"; // SQL query to get all employees

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_management", "root", "901017297");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                double salary = resultSet.getDouble("salary");

                employees.add(new Employee(name, role, salary)); // Add employee to list
            }

            employeeTable.setItems(employees); // Set the table's items to the list

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading employee data");
        }
    }

    @FXML
    public void handleDashboard() {
        System.out.println("Dashboard clicked");
        loadContent("Dashboard.fxml"); // Load the dashboard content dynamically
    }

    @FXML
    public void handleManageEmployees() {
        System.out.println("Manage Employees clicked");
        loadContent("ManageEmployees.fxml"); // Load the employee management content dynamically
        loadEmployeeData(); // Load employee data into the table
    }

    @FXML
    public void handleLogout() {
        System.out.println("Logging out...");

        // Create a confirmation dialog before logging out
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Scene loginScene = new Scene(loader.load());
                Stage stage = (Stage) contentArea.getScene().getWindow();
                stage.setScene(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to load content dynamically into the center of the layout
    private void loadContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane pane = loader.load();  // Load the FXML file
            contentArea.getChildren().setAll(pane); // Replace contentArea's children with the new content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Show error message if something goes wrong
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    // Employee class to hold employee data for the table
    public static class Employee {
        private final String name;
        private final String role;
        private final double salary;

        public Employee(String name, String role, double salary) {
            this.name = name;
            this.role = role;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        public double getSalary() {
            return salary;
        }
    }
}
