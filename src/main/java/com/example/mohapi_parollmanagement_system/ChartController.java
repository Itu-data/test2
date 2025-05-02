// controller/ChartController.java
package com.example.mohapi_parollmanagement_system;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import java.sql.*;

public class ChartController {
    @FXML private BarChart<String, Number> payrollChart;

    @FXML
    public void initialize() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        try (Connection conn = com.example.mohapipayroll_management_system.Database.connect();  // Corrected package
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT department, SUM(basic_salary) as total FROM employee GROUP BY department")) { // Fixed table name: "employee"

            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString("department"), rs.getDouble("total")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        payrollChart.getData().add(series);
    }
}
