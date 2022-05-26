package com.fadedbytes.colegio.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private final char classGroup;
    private final List<Course> courses;

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
        float average = 0;
        for (Task.StudentTask task : tasks) {
            average += task.getGrade();
        }

        return average / (float) (tasks.size() == 0 ? 1 : tasks.size()); // Prevenimos dividir entre 0
    }

    @Override
    public @NotNull String accessSchoolData() {
        StringBuilder sb = new StringBuilder();
        for (Course course : courses) {
            sb.append(course.toString()).append("\n");
        }
        return super.accessSchoolData() + "\n"
                + "Grupo: " + getClassGroup() + "\n"
                + "Cursos: \n" + sb + "\n"
                + "Media: " + getAverage();
    }

    public void markTaskAsDone(Task.StudentTask task) {
        markTaskAsDone(task.getTask());
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void markTaskAsDone(Task task) {
        this.getTasks().forEach(t -> {
            if (t.getTask().equals(task)) {
                t.complete();
            }
        });
    }

    public ArrayList<Task.StudentTask> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void addTask(Task.StudentTask task) {
        tasks.add(task);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    @Override
    public String toString() {
        return getID() + " - " + getName() + (getEmail().isEmpty() ? "" : " (" + getEmail() + ")") + " - " + getClassGroup() + " - " + getAverage();
    }
}
