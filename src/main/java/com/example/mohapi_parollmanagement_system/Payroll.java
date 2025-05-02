package com.example.mohapi_parollmanagement_system;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class Payroll {

    @FXML
    private TextField employeeIdField, overtimeField, deductionsField;

    @FXML
    private Label statusLabel;

    private Connection conn;

    public Payroll() {
        try {
            DatabaseMetaData DatabaseConnection = null;
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Payroll(int payrollId, int employeeId, double grossSalary, double netSalary) {
    }

    @FXML
    private void handleCalculatePayroll() {
        try {
            int empId = Integer.parseInt(employeeIdField.getText());
            double overtime = Double.parseDouble(overtimeField.getText());
            double deductions = Double.parseDouble(deductionsField.getText());

            // Fetch basic salary
            PreparedStatement stmt = conn.prepareStatement("SELECT basic_salary FROM employee WHERE employee_id = ?");
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double basicSalary = rs.getDouble("basic_salary");
                double grossSalary = basicSalary + overtime;
                double netSalary = grossSalary - deductions;

                // Insert payroll record
                PreparedStatement insert = conn.prepareStatement(
                        "INSERT INTO payroll (employee_id, basic_salary, overtime, deductions, gross_salary, net_salary) VALUES (?, ?, ?, ?, ?, ?)");
                insert.setInt(1, empId);
                insert.setDouble(2, basicSalary);
                insert.setDouble(3, overtime);
                insert.setDouble(4, deductions);
                insert.setDouble(5, grossSalary);
                insert.setDouble(6, netSalary);
                insert.executeUpdate();

                statusLabel.setText("Payroll calculated and saved.");
                statusLabel.setStyle("-fx-text-fill: green;");
            } else {
                statusLabel.setText("Employee not found.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid input.");
            statusLabel.setStyle("-fx-text-fill: red;");
        } catch (SQLException e) {
            statusLabel.setText("Database error.");
            e.printStackTrace();
        }
    }

    public double getGrossSalary() {
        return 0;
    }

    public double getNetSalary() {
   return 0;
    }

    public int getPayrollId() {
    return 0;
    }
}
