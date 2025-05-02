package com.example.mohapi_parollmanagement_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageUsersController {

    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
        AnchorPane pane = loader.load();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(pane));
        stage.show();
    }
}
