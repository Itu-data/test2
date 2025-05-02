package com.example.mohapi_parollmanagement_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class EmployeeDashboardController {

    @FXML
    private void handleViewProfile() {
        // Add profile viewing logic
        System.out.println("Loading employee profile...");
    }

    @FXML
    private void handleSubmitRequest() {
        // Add request submission logic
        System.out.println("Opening request form...");
    }

    @FXML
    private void handleViewPayslips() {
        // Add payslip viewing logic
        System.out.println("Loading payslip history...");
    }

    @FXML
    private void handleLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        AnchorPane loginScreen = loader.load();
        Stage stage = (Stage) loginScreen.getScene().getWindow();
        stage.setScene(new Scene(loginScreen));
    }
}