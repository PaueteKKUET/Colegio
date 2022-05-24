package com.fadedbytes.colegio;

import com.fadedbytes.colegio.client.MainMenu;
import com.fadedbytes.colegio.database.DatabaseManager;

import javax.swing.*;
import java.sql.SQLException;

public class Start {

    public static void main(String[] args) {
        //if (!setup()) return;

        System.out.println("Colegio abierto correctamente :)");

        JFrame frame = initGUI();
    }

    private static boolean setup() {
        try {
            DatabaseManager.connect();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static JFrame initGUI() {
        JFrame frame = new JFrame("MainMenu");
        frame.setContentPane(new MainMenu().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setSize(300, 450);

        return frame;
    }

}
