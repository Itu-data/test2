package com.example.mohapi_parollmanagement_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ManagerDashboardController {

    @FXML
    private void handleViewTeam() throws IOException {
        try {
            // Fetch team details from the database
            String teamDetails = fetchTeamDetails();

            // Load team view screen and pass the data
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TeamView.fxml"));
            AnchorPane teamView = loader.load();
            TeamViewController controller = loader.getController();
            controller.setTeamDetails(teamDetails);

            // Get the current stage and switch the scene
            Stage stage = (Stage) teamView.getScene().getWindow();
            stage.setScene(new Scene(teamView));
            System.out.println("Loading team details...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String fetchTeamDetails() throws SQLException {
        String query = "SELECT * FROM teams"; // Adjust this query as needed
        StringBuilder details = new StringBuilder();

        DatabaseMetaData Database = null;
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                details.append(resultSet.getString("team_name")).append("\n"); // Example column name
            }
        }
        return details.toString();
    }

    // Other methods for handling approvals, metrics, and logout can be added here
}