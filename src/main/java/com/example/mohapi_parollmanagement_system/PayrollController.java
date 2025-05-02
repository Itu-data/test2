package com.example.mohapi_parollmanagement_system;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.beans.property.*;

import java.sql.*;

public class PayrollController {

    @FXML private TextField nameField, positionField, departmentField, basicSalaryField, overtimeField, deductionsField;
    @FXML private Button addEmployeeButton, generatePayslipButton, updateSalaryButton;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> nameColumn, positionColumn, departmentColumn;
    @FXML private TableView<Payroll> payrollTable;
    @FXML private TableColumn<Payroll, Double> grossSalaryColumn, netSalaryColumn;

    private Connection conn;

    public PayrollController() {
        try {
            // Establishing the database connection
            String url = "jdbc:mysql://localhost:3306/payroll_management";  // Replace with your database URL
            String user = "root";  // Your database username
            String password = "901017297";  // Your database password
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Setup employee table columns
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        positionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
        departmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartment()));

        // Setup payroll table columns
        grossSalaryColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getGrossSalary()).asObject());
        netSalaryColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getNetSalary()).asObject());

        // Load initial data for both tables
        loadEmployeeData();
        loadPayrollData();
    }

    @FXML
    private void handleAddEmployee(ActionEvent event) {
        String name = nameField.getText();
        String position = positionField.getText();
        String department = departmentField.getText();

        if (name.isEmpty() || position.isEmpty() || department.isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        try {
            double basicSalary = Double.parseDouble(basicSalaryField.getText());
            double overtime = Double.parseDouble(overtimeField.getText());
            double deductions = Double.parseDouble(deductionsField.getText());

            // Insert employee into the employee table
            String empQuery = "INSERT INTO employee (name, position, department) VALUES (?, ?, ?)";
            PreparedStatement empStmt = conn.prepareStatement(empQuery, Statement.RETURN_GENERATED_KEYS);
            empStmt.setString(1, name);
            empStmt.setString(2, position);
            empStmt.setString(3, department);
            int rows = empStmt.executeUpdate();

            if (rows > 0) {
                ResultSet keys = empStmt.getGeneratedKeys();
                if (keys.next()) {
                    int employeeId = keys.getInt(1);

                    // Insert into payroll
                    double grossSalary = basicSalary + overtime;
                    double netSalary = grossSalary - deductions;

                    String payrollQuery = "INSERT INTO payroll (employee_id, gross_salary, net_salary, basic_salary, overtime, deductions) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement payrollStmt = conn.prepareStatement(payrollQuery);
                    payrollStmt.setInt(1, employeeId);
                    payrollStmt.setDouble(2, grossSalary);
                    payrollStmt.setDouble(3, netSalary);
                    payrollStmt.setDouble(4, basicSalary);
                    payrollStmt.setDouble(5, overtime);
                    payrollStmt.setDouble(6, deductions);
                    payrollStmt.executeUpdate();

                    showAlert("Success", "Employee and payroll added.");
                    loadEmployeeData();
                    loadPayrollData();
                }
            } else {
                showAlert("Error", "Failed to add employee.");
            }

        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter valid numbers for salary fields.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error occurred.");
        }
    }

    @FXML
    private void handleGeneratePayslip(ActionEvent event) {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            String filePath = "Payslip_Employee_" + selectedEmployee.getEmployeeId() + ".pdf";
            PDFGenerator.generatePayslip(selectedEmployee.getEmployeeId(), filePath);
            showAlert("Success", "Payslip generated at: " + filePath);
        } else {
            showAlert("Error", "Please select an employee.");
        }
    }

    @FXML
    private void handleUpdateSalary(ActionEvent event) {
        Payroll selectedPayroll = payrollTable.getSelectionModel().getSelectedItem();
        if (selectedPayroll != null) {
            try {
                String query = "UPDATE payroll SET gross_salary = ?, net_salary = ? WHERE payroll_id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setDouble(1, selectedPayroll.getGrossSalary());
                stmt.setDouble(2, selectedPayroll.getNetSalary());
                stmt.setInt(3, selectedPayroll.getPayrollId());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Success", "Salary updated successfully.");
                    loadPayrollData();
                } else {
                    showAlert("Error", "Failed to update salary.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Database error occurred.");
            }
        } else {
            showAlert("Error", "Please select a payroll record.");
        }
    }

    private void loadEmployeeData() {
        try {
            String query = "SELECT * FROM employee";
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            employeeTable.getItems().clear();
            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getString("department")
                );
                employeeTable.getItems().add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPayrollData() {
        try {
            String query = "SELECT * FROM payroll";
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            payrollTable.getItems().clear();
            while (resultSet.next()) {
                Payroll payroll = new Payroll(
                        resultSet.getInt("payroll_id"),
                        resultSet.getInt("employee_id"),
                        resultSet.getDouble("gross_salary"),
                        resultSet.getDouble("net_salary")
                );
                payrollTable.getItems().add(payroll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
