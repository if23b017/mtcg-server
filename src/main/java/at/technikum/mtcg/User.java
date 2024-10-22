package at.technikum.mtcg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean register() {
        String insertUserSQL = "INSERT INTO users (username, password, coins) VALUES (?, ?, 20)";

        try (Connection conn = DbAccess.connect();
             PreparedStatement stmt = conn.prepareStatement(insertUserSQL)) {

            stmt.setString(1, this.username);
            stmt.setString(2, this.password);

            stmt.executeUpdate();
            System.out.println("Registrierung erfolgreich!");
            return true;

        } catch (SQLException e) {
            System.out.println("Fehler bei der Registrierung: " + e.getMessage());
            return false;
        }
    }

    public boolean login() {
        String selectUserSQL = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DbAccess.connect();
             PreparedStatement stmt = conn.prepareStatement(selectUserSQL)) {

            stmt.setString(1, this.username);
            stmt.setString(2, this.password);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login erfolgreich!");
                return true;
            } else {
                System.out.println("Login fehlgeschlagen: Falscher Benutzername oder Passwort.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Login: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        User newUser = new User("testuser2", "testpassword2");

        boolean registrationSuccessful = newUser.register();
        if (registrationSuccessful) {
            System.out.println("Benutzer erfolgreich registriert!");
        } else {
            System.out.println("Registrierung fehlgeschlagen.");
        }

        User loginUser = new User("testuser", "testpassword");
        boolean loginSuccessful = loginUser.login();
        if (loginSuccessful) {
            System.out.println("Login erfolgreich!");
        } else {
            System.out.println("Login fehlgeschlagen: Falscher Benutzername oder Passwort.");
        }
    }
}