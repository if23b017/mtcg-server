package at.technikum.mtcg.utils;

import at.technikum.mtcg.database.DbAccess;
import at.technikum.mtcg.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseUtil {
    public static boolean registerUser(User user) {
        String checkUserSQL = "SELECT * FROM users WHERE username = ?";
        String insertUserSQL = "INSERT INTO users (username, password, coins) VALUES (?, ?, 20)";

        try (Connection conn = DbAccess.connect()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserSQL)) {
                checkStmt.setString(1, user.getUsername());
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Username already taken:" + user.getUsername());
                    return false;
                }
            }
            String salt = PasswordUtil.generateSalt();
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword(), salt);
            try (PreparedStatement insertStmt = conn.prepareStatement(insertUserSQL)) {
                insertStmt.setString(1, user.getUsername());
                insertStmt.setString(2, hashedPassword);
                insertStmt.executeUpdate();
                System.out.println("Registration successful!");
                return true;
            } catch (SQLException e) {
                System.out.println("Error during registration: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String loginUser(User user) {
        String selectUserSQL = "SELECT * FROM users WHERE username = ?";
        String updateTokenSQL = "UPDATE users SET token = ? WHERE username = ?";

        try (Connection conn = DbAccess.connect(); PreparedStatement stmt = conn.prepareStatement(selectUserSQL)) {
            stmt.setString(1, user.getUsername());
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("password");

                String[] parts = storedHash.split(":");
                if (parts.length != 2) {
                    throw new RuntimeException("Hash is not in the correct format.");
                }
                String salt = parts[0];
                String expectedHash = parts[1];
                String hashedPassword = PasswordUtil.hashPassword(user.getPassword(), salt).split(":")[1];

                if (hashedPassword.equals(expectedHash)) {
                    String token = user.generateAuthToken();
                    try(PreparedStatement updateStmt = conn.prepareStatement(updateTokenSQL)) {
                        updateStmt.setString(1, token);
                        updateStmt.setString(2, user.getUsername());
                        updateStmt.executeUpdate();
                    }
                    System.out.println("Login successful! " + user.getUsername());
                    return token;
                }
            }
            return null;

        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
            System.out.println("Error during login: " + e.getMessage());
            return null;
        }
    }

    public static boolean isValidToken(String token) {
        String selectTokenSQL = "SELECT * FROM users WHERE token = ?";

        try (Connection conn = DbAccess.connect();
             PreparedStatement stmt = conn.prepareStatement(selectTokenSQL)) {
            stmt.setString(1, token);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error during token validation: " + e.getMessage());
            return false;
        }
    }
}
