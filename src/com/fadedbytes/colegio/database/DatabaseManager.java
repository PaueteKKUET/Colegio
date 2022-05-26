package com.fadedbytes.colegio.database;

import jdk.swing.interop.SwingInterOpUtils;
import org.postgresql.util.HostSpec;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class DatabaseManager {

    private static final String HOST = "localhost";
    private static final int PORT = 5432;
    private static final String DATABASE = "Colegio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    static Connection connection = null;

    public static void connect() throws SQLException {

        HostSpec[] hosts = new HostSpec[] {
            new HostSpec(HOST, PORT)
        };

        String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE;

        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "alcachofa");
        props.setProperty("ssl", "false");

        connection = DriverManager.getConnection(url, props);

        if (connection != null) {
            System.out.println("Connected to the database");
        } else {
            System.out.println("Failed to make connection!");
        }

        createTaskTable();
    }

    private static void createTaskTable() throws SQLException {
        if (connection == null) throw new IllegalStateException("No connection to database");

        query("""
            CREATE TABLE IF NOT EXISTS task(
                id integer primary key not null,
                name VARCHAR(20),
                description VARCHAR(200),
                course_name VARCHAR(20)
            );
        """, false);
    }

    public static Optional<ResultSet> query(String query, boolean expectResult) throws SQLException {
        if (connection == null) throw new IllegalStateException("No connection to database");

        Statement statement = connection.createStatement();
        if (expectResult) {
            return Optional.of(statement.executeQuery(query));
        } else {
            statement.execute(query);
            return Optional.empty();
        }
    }

}
