package com.fadedbytes.colegio.database;

import org.postgresql.jdbc.PgConnection;
import org.postgresql.util.HostSpec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {

    private static final String HOST = "localhost";
    private static final int PORT = 5432;
    private static final String DATABASE = "colegio";
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
        props.setProperty("password", "postgres");
        props.setProperty("ssl", "true");

        connection = DriverManager.getConnection(url, props);

        if (connection != null) {
            System.out.println("Connected to the database");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

}
