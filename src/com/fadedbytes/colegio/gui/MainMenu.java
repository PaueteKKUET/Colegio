package com.fadedbytes.colegio.gui;

import com.fadedbytes.colegio.api.*;
import com.fadedbytes.colegio.database.TaskToDatabase;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainMenu {
    public JPanel background;
    private JTabbedPane tabs;
    private JPanel tabInicio;
    private JPanel tabLoad;
    private JTextField inputID;
    private JButton loginButton;
    private JLabel textBienvenida;
    private JLabel textEmail;
    private JLabel textGroup;
    private JLabel textMedia;
    private JLabel emailValue;
    private JLabel groupValue;
    private JLabel mediaValue;
    private JScrollPane coursePane;
    private JList courseList;
    private JPanel tabTareas;
    private JTable taskTable;
    private JButton leerTareaButton;
    private JButton guardarTareaButton;
    private JButton dbReadButton;
    private JScrollPane tablePane;

    public MainMenu() {
        loginButton.addActionListener(e -> login(inputID.getText()));

        updateUser(null);
        dbReadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                readDatabase();
            }
        });
        guardarTareaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (taskTable.getSelectedRow() == -1) {
                    cry("Selecciona una tarea primero");
                    return;
                }
                saveTask((Integer) taskTable.getValueAt(taskTable.getSelectedRow(), 0));
                cry("Tarea guardada");
            }
        });
        leerTareaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String taskID = JOptionPane.showInputDialog("Introduce el ID de la tarea");
                try {
                    readTask(Integer.parseInt(taskID));
                } catch (NumberFormatException ex) {
                    cry("El ID debe ser un número");
                }
            }
        });
    }

    private void login(String id) {
        User user;
        try {
            user = User.getUser(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            cry("El ID no es válido, debe ser un número entero");
            return;
        }

        if (user == null) {
            cry("El ID no existe");
        } else {
            tabs.setSelectedIndex(1);
        }

        updateUser(user);

    }

    private void readDatabase() {
        TaskToDatabase.saveTasks();

        Collection<Task> tasks = TaskToDatabase.getStoredTasks();
        setupTable(tasks.toArray(new Task[0]));

        cry("Base de datos sincronizada");
    }

    private void saveTask(int id) {
        Task task = null;
        for (Task t : Task.getAllTasks()) {
            if (t.getId() == id) {
                task = t;
            }
        }

        if (task != null) {

            File dataFolder = new File("data");
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            try (
                    FileOutputStream fs = new FileOutputStream("data\\task_" + task.getId() + ".ser");
                    ObjectOutputStream os = new ObjectOutputStream(fs)
            ) {
                os.writeObject(task);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void readTask() {

    }

    private void updateUser(User user) {
        if (user == null) {
            emailValue.setVisible(false);
            textEmail.setVisible(false);

            groupValue.setVisible(false);
            textGroup.setVisible(false);

            mediaValue.setVisible(false);
            textMedia.setVisible(false);

            coursePane.setVisible(false);

            textBienvenida.setText("Inicia sesión usando la pestaña Cargar con ID");

            return;
        }

        coursePane.setVisible(true);

        textBienvenida.setText("Bienvenido, " + user.getName() + ", eres un " + user.getClass().getSimpleName().toLowerCase() + " con los siguientes datos:");
        textEmail.setVisible(true);
        emailValue.setVisible(true);

        emailValue.setText(user.getEmail());

        Collection<Task> tasks = new ArrayList<>();
        if (user instanceof Student student) {
            Collection<Task> finalTasks = tasks;
            student.getTasks().forEach(task -> finalTasks.add(task.getTask()));
            tasks = finalTasks;

            textGroup.setVisible(true);
            groupValue.setVisible(true);

            textGroup.setText("Grupo");
            groupValue.setText(String.valueOf(student.getClassGroup()));

            textMedia.setVisible(true);
            mediaValue.setVisible(true);

            mediaValue.setText(String.valueOf(student.getAverage()));

            List<Course> courses = student.getCourses();
            DefaultListModel<String> listModel = new DefaultListModel<>();

            for (Course course : courses) {
                listModel.addElement(course.getName());
            }

            courseList.setModel(listModel);
        } else if (user instanceof Professor professor) {

            System.out.println("cursos: " + professor.getCourses().length);
            Collection<Task> finalTasks = tasks;
            Arrays.stream(professor.getCourses())
                    .forEach(course -> {
                        finalTasks.addAll(course.getTasks());
                    });
            tasks = finalTasks;

            textGroup.setVisible(true);
            groupValue.setVisible(true);

            textGroup.setText("Salario:");
            groupValue.setText(String.valueOf(professor.getSalary()));

            DefaultListModel<String> listModel = new DefaultListModel<>();
            Course.getAllCourses()
                    .stream()
                    .filter(course -> course.getProfessor().equals(professor))
                    .forEach(course -> listModel.addElement(course.getName()));

            courseList.setModel(listModel);
        }

        setupTable(tasks.toArray(new Task[0]));

    }

    private static final String[] tableColumns = {
            "ID",
            "Nombre",
            "Descripción",
            "Curso"
    };

    private void setupTable(@Nullable Task... tasks) {
        if (tasks == null) {
            taskTable.setModel(new DefaultTableModel(new Object[][]{}, tableColumns));
        } else {
            Object[][] data = new Object[tasks.length][];
            for (int i = 0; i < tasks.length; i++) {
                data[i] = new Object[]{
                        tasks[i].getId(),
                        tasks[i].getName(),
                        tasks[i].getDescription(),
                        tasks[i].getCourse().getName()
                };
            }
            taskTable.setModel(new DefaultTableModel(data, tableColumns));
        }
    }

    private void readTask(int taskNumber) {
        Task task = null;
        try (
                FileInputStream fs = new FileInputStream("task_" + taskNumber + ".ser");
                ObjectInputStream is = new ObjectInputStream(fs)
        ) {

            task = (Task) is.readObject();

        } catch (IOException | ClassNotFoundException e) {
            cry("Esa tarea no existe");
        }

        if (task != null) {
            JOptionPane.showMessageDialog(background, String.format(
                    """
                            Número de tarea: %s
                            Nombre: %s
                            Descripción: %s
                            """,
                    task.getId(),
                    task.getName(),
                    task.getDescription()
            ));
        }
    }

    private void cry(String message) {
        JOptionPane.showMessageDialog(background, message);
    }

}
