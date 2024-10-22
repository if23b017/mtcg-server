package at.technikum.mtcg;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbAccess {

    public static Connection connect() throws SQLException {
        Properties props = new Properties();

        try (InputStream input = DbAccess.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Konfigurationsdatei 'config.properties' wurde nicht gefunden");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Konfigurationsdatei", e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }



    public static void main(String[] args) {
        try (Connection connection = DbAccess.connect()) {
            if (connection != null) {
                System.out.println("Verbindung zur Datenbank erfolgreich hergestellt!");
            }
        } catch (SQLException e) {
            System.out.println("Fehler bei der Verbindung zur Datenbank: " + e.getMessage());
        }
    }

}