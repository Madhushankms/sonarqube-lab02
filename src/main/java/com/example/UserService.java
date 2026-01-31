package com.example;   // Make sure this matches your folder path

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

    // Logger
    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    // Database configuration (No hardcoded credentials)
    private static final String DB_URL =
            "jdbc:mysql://localhost/db";

    private static final String DB_USER =
            "root";

    private static final String DB_PASSWORD =
            System.getenv("DB_PASSWORD");

    /**
     * Find user by username
     */
    public void findUser(String username) throws SQLException {

        // Use column names instead of SELECT *
        String query =
                "SELECT id, name, email, role FROM users WHERE name = ?";

        // try-with-resources auto closes resources
        try (Connection conn =
                     DriverManager.getConnection(
                             DB_URL, DB_USER, DB_PASSWORD);

             PreparedStatement ps =
                     conn.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String role = rs.getString("role");

                    logger.info(
                            "User found: id={}, name={}, email={}, role={}",
                            id, name, email, role
                    );

                } else {

                    logger.warn("User not found: {}", username);
                }
            }
        }
    }

    /**
     * Delete user by username
     */
    public void deleteUser(String username) throws SQLException {

        String query =
                "DELETE FROM users WHERE name = ?";

        try (Connection conn =
                     DriverManager.getConnection(
                             DB_URL, DB_USER, DB_PASSWORD);

             PreparedStatement ps =
                     conn.prepareStatement(query)) {

            ps.setString(1, username);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                logger.warn("User deleted: {}", username);

            } else {

                logger.info("No user found to delete: {}", username);
            }
        }
    }
}
