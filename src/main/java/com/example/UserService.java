package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Custom exception to handle User related database errors specifically.
 */
class UserServiceException extends Exception {
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class UserService {

    // FIXED (java:S1170): Made final fields 'static' to treat them as true constants
    private static final String DB_URL = "jdbc:mysql://localhost/db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD"); 

    /**
     * Finds a user safely using PreparedStatement.
     * FIXED (java:S6905): Replaced "SELECT *" with explicit column names.
     */
    public void findUser(String username) throws UserServiceException {
        // Retrieve only the columns necessary for the application logic
        String query = "SELECT id, name, email FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.executeQuery();
            
        } catch (SQLException e) {
            throw new UserServiceException("Error searching for user: " + username, e);
        }
    }

    /**
     * Deletes a user safely.
     */
    public void deleteUser(String username) throws UserServiceException {
        String query = "DELETE FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new UserServiceException("Error deleting user: " + username, e);
        }
    }
}