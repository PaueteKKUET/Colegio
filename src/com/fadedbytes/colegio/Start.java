package com.fadedbytes.colegio;

import com.fadedbytes.colegio.api.Course;
import com.fadedbytes.colegio.api.Professor;
import com.fadedbytes.colegio.api.Student;
import com.fadedbytes.colegio.api.Task;
import com.fadedbytes.colegio.gui.MainMenu;
import com.fadedbytes.colegio.database.DatabaseManager;
import com.fadedbytes.colegio.database.TaskToDatabase;

import javax.swing.*;
import java.io.*;
import java.sql.SQLException;

public class Start {

    public static void main(String[] args) {
        if (!setup()) return;

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

        setupTests();

        return true;
    }

    private static JFrame initGUI() {
        JFrame frame = new JFrame("IES Pedro Mariano Cortes y Fairy");
        frame.setContentPane(new MainMenu().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setSize(600, 450);



        return frame;
    }

    private static void setupTests() {
        Professor[] professors = {
                new Professor("Pepe", "pepe@iesperemaria.com", 1000),
                new Professor("Ana", "ana@iesperemaria.com", 2000),
                new Professor("Leo", "leo@iesperemaria.com", 8000)
        };

        Student[] students = {
                new Student("Antonio", "antonio@iesperemaria.com", 'A', null),
                new Student("Susana", "susana@iesperemaria.com", 'A', null),
                new Student("Jose", "jose@iesperemaria.com", 'B', null),
                new Student("Ines", "ines@iesperemaria.com", 'C', null)
        };

        Course c = new Course("Programación", professors[0]);
        Course c2 = new Course("Hacedura de galletas", professors[2]);
        Course c3 = new Course("Crianza de alcachofas", professors[2]);
        Course c4 = new Course("Barrido de desiertos", professors[2]);
        Course c5 = new Course("Extracción de sardinas", professors[1]);

        Task[] tasks = {
                new Task("Talar un tonco", "Tala un tronco con tus propias manos", c),
                new Task("Haz una tarta", "Haz una tarta a partir de un martillo pilón", c),
                new Task("Toma una foto", "Toma una foto mediante un palo y un aro", c)
        };
    }

}
