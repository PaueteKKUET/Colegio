package com.fadedbytes.colegio.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private char classGroup;
    private List<Course> courses;

    private float average;
    private final ArrayList<Task.StudentTask> tasks;

    public Student() {
        this("Noten Gonom Bré", null, '\0', null);
    }

    public Student(@NotNull String name, @Nullable String email, char classGroup, @Nullable ArrayList<Task.StudentTask> tasks) {
        super(name, email);
        this.classGroup = classGroup;
        this.tasks = tasks == null ? new ArrayList<>() : tasks;

        this.courses = new ArrayList<>();
    }

    public char getClassGroup() {
        return classGroup;
    }

    public float getAverage() {
        average = 0;
        for (Task.StudentTask task : tasks) {
            average += task.getGrade();
        }

        return average /= tasks.size();
    }

    @Override
    public @NotNull String accessSchoolData() {
        StringBuilder sb = new StringBuilder();
        for (Course course : courses) {
            sb.append(course.toString()).append("\n");
        }
        return super.accessSchoolData() + "\n"
                + "Cursos: \n" + sb.toString()
                + "Media: " + getAverage();
    }

    public void markTaskAsDone(Task.StudentTask task) {
        this.tasks.remove(task);
    }

    public ArrayList<Task.StudentTask> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public String toString() {
        return getID() + " - " + getName() + (getEmail().isEmpty() ? "" : " (" + getEmail() + ")") + " - " + getClassGroup() + " - " + getAverage();
    }
}
