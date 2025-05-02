package com.example.mohapi_parollmanagement_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String DATABASE_NAME = "Payroll_management";  // Ensure this matches your actual database name
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;

    private static final String USER = "root";  // Using the root user
    private static final String PASSWORD = "901017297";  // Correct root password

    /**
     * Initializes the database connection.
     *
     * @return Connection object if successful.
     * @throws SQLException if a connection cannot be established.
     */
    public static Connection initialize() throws SQLException {
        try {
            // Load MySQL JDBC Driver (optional in modern versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish and return the connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found.");
            e.printStackTrace();
            throw new SQLException("Driver not found", e);
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            throw e;
        }
    }
}
