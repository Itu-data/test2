package com.example.mohapi_parollmanagement_system;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList("admin", "manager", "employee"));
    }

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            System.out.println("Please fill in all fields.");
            return;
        }

        if ("admin".equalsIgnoreCase(username) && "admin".equals(password) && "admin".equalsIgnoreCase(role)) {
            loadDashboard("AdminDashboard.fxml");
        } else if ("manager".equalsIgnoreCase(username) && "manager".equals(password) && "manager".equalsIgnoreCase(role)) {
            loadDashboard("ManagerDashboard.fxml");
        } else if ("employee".equalsIgnoreCase(username) && "employee".equals(password) && "employee".equalsIgnoreCase(role)) {
            loadDashboard("EmployeeDashboard.fxml");
        } else {
            System.out.println("Invalid credentials or role.");
        }
    }

    private void loadDashboard(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToSignUp() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
