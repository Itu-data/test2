package com.example.mohapipayroll_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // JDBC URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/payroll_management";  // Your DB URL
    private static final String USER = "root";  // Your DB username
    private static final String PASSWORD = "901017297";  // Your DB password

    // Static connection variable
    private static Connection connection = null;

    // Method to establish and return a database connection
    public static Connection connect() {
        if (connection == null) {
            try {
                // Establish the connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully.");
            } catch (SQLException e) {
                e.printStackTrace(); // Log the exception
                System.out.println("Database connection failed.");
            }
        }
        return connection;
    }

    // Method to close the database connection
    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Reset the connection variable
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace(); // Log the exception
                System.out.println("Failed to close the database connection.");
            }
        }
    }
}