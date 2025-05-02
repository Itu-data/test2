package com.example.mohapipayroll_management_system;

import com.example.mohapi_parollmanagement_system.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageEmployeesController {

    @FXML private TextField nameField;
    @FXML private TextField departmentField;
    @FXML private TextField positionField;

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer> idColumn;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> departmentColumn;
    @FXML private TableColumn<Employee, String> positionColumn;

    @FXML
    public void initialize() {
        // Use PropertyValueFactory for simple JavaBeans (no JavaFX properties needed)
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        try {
            loadEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddEmployee() {
        String name = nameField.getText();
        String department = departmentField.getText();
        String position = positionField.getText();

        if (name.isEmpty() || department.isEmpty() || position.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        Employee newEmployee = new Employee(0, name, department, position);

        try {
            addEmployeeToDatabase(newEmployee);
            loadEmployees(); // Refresh the table
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Failed to add employee.");
        }
    }

    private void addEmployeeToDatabase(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (name, department, position) VALUES (?, ?, ?)";

        try (Connection connection = Database.connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getDepartment());
            stmt.setString(3, employee.getPosition());
            stmt.executeUpdate();
        }
    }

    private void loadEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection connection = Database.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("position")
                );
                employees.add(emp);
            }
        }

        employeeTable.getItems().setAll(employees);
    }

    private void clearFields() {
        nameField.clear();
        departmentField.clear();
        positionField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
