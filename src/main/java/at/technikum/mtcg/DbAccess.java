package at.technikum.mtcg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {
    private static final String URL = "jdbc:postgresql://localhost:5432/mtcg";
    private static final String USER = "if23b017";
    private static final String PASSWORD = "Passwort1";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}