package main.java.com.example;

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

    // Database configuration (NO hardcoded password)
    private static final String DB_URL =
            "jdbc:mysql://localhost/db";

    private static final String DB_USER =
            "root";

    private static final String DB_PASSWORD =
            System.getenv("DB_PASSWORD");

    // Find user (Secure + Closed Resources)
    public void findUser(String username) throws SQLException {

        String query =
                "SELECT * FROM users WHERE name = ?";

        // try-with-resources closes Connection, Statement, ResultSet automatically
        try (Connection conn =
                     DriverManager.getConnection(
                             DB_URL, DB_USER, DB_PASSWORD);

             PreparedStatement ps =
                     conn.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    logger.info("User found: {}", username);
                } else {
                    logger.warn("User not found: {}", username);
                }
            }
        }
    }

    // Delete user (Secure + No Generic Exception)
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
                logger.info("No user to delete: {}", username);
            }
        }
    }
}
