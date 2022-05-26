package com.fadedbytes.colegio.database;

import com.fadedbytes.colegio.api.Course;
import com.fadedbytes.colegio.api.Task;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class TaskToDatabase {

    public static void toDatabase(@NotNull Task task) {
        try {
            DatabaseManager.query(
                    String.format("""
                        INSERT INTO task
                        VALUES (%s, '%s', '%s', '%s');
                        """,
                        String.valueOf(task.getId()),
                        task.getName(),
                        task.getDescription(),
                        task.getCourse().getName()
                    ),
                    false
            );
        } catch (SQLException e) {
            System.out.println("La tarea con id (" + task.getId() + ") ya está almacenada en la base de datos, omitiendo...");
        }
    }

    public static void saveTasks() {
        Task.getAllTasks().forEach(TaskToDatabase::toDatabase);
    }

    public static Collection<Task> getStoredTasks() {
        Collection<Task> tasks = new ArrayList<>();
        try {
            DatabaseManager.query("SELECT * FROM task;", true).ifPresent(queryResult -> {
                try {
                    while (queryResult.next()) {
                        Task task = new Task(queryResult.getString("name"), queryResult.getString("description"), Course.byName(queryResult.getString("course_name")));

                        Field idField = task.getClass().getDeclaredField("ID");
                        idField.setAccessible(true);

                        idField.setInt(task, queryResult.getInt("id"));

                        tasks.add(task);

                    }
                } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;

    }

}
