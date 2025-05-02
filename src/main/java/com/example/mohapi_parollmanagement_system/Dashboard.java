package com.example.mohapi_parollmanagement_system;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dashboard {

    @FXML
    private BarChart<String, Number> salaryChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    public void initialize() {
        loadSalaryData();
    }

    private void loadSalaryData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Employee Salaries");

        String url = "jdbc:mysql://localhost:3306/payroll_management";
        String user = "root";
        String password = "901017297"; // Change if your DB has a password

        String query = "SELECT name, salary FROM employees";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                series.getData().add(new XYChart.Data<>(name, salary));
            }

            salaryChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
