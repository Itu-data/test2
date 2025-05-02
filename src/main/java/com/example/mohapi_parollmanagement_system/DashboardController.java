package com.example.mohapi_parollmanagement_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {

    // FXML components
    @FXML
    private Button addEmployeeButton, managePayrollButton, generateReportButton, viewPayslipButton;

    @FXML
    private AnchorPane contentPane;  // To load different screens in the dashboard

    // This method will handle the click event to add an employee
    @FXML
    private void handleAddEmployee() {
        // You could load the Add Employee screen or form here
        loadScreen("AddEmployeeScreen.fxml");
    }

    // This method will handle the click event to manage payroll
    @FXML
    private void handleManagePayroll() {
        // You could load the Manage Payroll screen here
        loadScreen("ManagePayrollScreen.fxml");
    }

    // This method will handle the click event to generate a report
    @FXML
    private void handleGenerateReport() {
        // Logic to generate a payroll report (e.g., summary, charts, etc.)
        showAlert("Feature Under Development", "Generating payroll reports is not yet implemented.");
    }

    // This method will handle the click event to view a payslip
    @FXML
    private void handleViewPayslip() {
        // Logic to view the payslip for an employee (maybe prompt for employee selection)
        showAlert("Feature Under Development", "Viewing payslips is not yet implemented.");
    }

    // Method to load a new screen into the content pane
    private void loadScreen(String fxmlFile) {
        try {
            // Load the screen using FXMLLoader
            AnchorPane newScreen = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentPane.getChildren().clear();  // Clear the current screen
            contentPane.getChildren().add(newScreen);  // Add the new screen
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load screen: " + fxmlFile);
        }
    }

    // Method to display an alert with a title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
