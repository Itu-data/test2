// controller/EmployeeController.java
package com.example.mohapi_parollmanagement_system;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import java.sql.*;

public class EmployeeController {
    @FXML private TextField nameField;
    @FXML private TextField deptField;
    @FXML private TextField positionField;
    @FXML private TextField basicSalaryField;
    @FXML private TextField hoursField;

    @FXML
    private void handleAddEmployee() {
        String name = nameField.getText();
        String dept = deptField.getText();
        String pos = positionField.getText();
        double salary = Double.parseDouble(basicSalaryField.getText());
        double hours = Double.parseDouble(hoursField.getText());

        String query = "INSERT INTO employees(name, department, position, basic_salary, working_hours) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = com.example.mohapipayroll_management_system.Database.connect(); // Correct import for Database
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, dept);
            stmt.setString(3, pos);
            stmt.setDouble(4, salary);
            stmt.setDouble(5, hours);
            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Employee Added Successfully");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
