package com.fadedbytes.colegio.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.management.AttributeNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Professor extends User implements Sobornable {

    private float salary;
    private Course[] courses;

    public Professor() {
        this("Profe Tá", null, 0.0f);
    }

    public Professor(@NotNull String name, @Nullable String email, float salary) {
        super(name, email);
        this.salary = salary;
        courses = new Course[0];
    }

    @Override
    public @NotNull String accessSchoolData() {
        StringBuilder sb = new StringBuilder();

        for (Course course : getCourses()) {
            sb.append(course.getName()).append("\n");
        }

        return super.accessSchoolData() + "\n"
                + "Salario: " + getSalary() + "€ al mes"
                + "\nCursos: " + sb;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @NotNull
    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(@NotNull Course[] courses) {
        this.courses = courses;
    }

    public void addCourse(@NotNull Course course) {

        course.setProfessor(this);

        Course[] newCourses = new Course[getCourses().length + 1];
        System.arraycopy(getCourses(), 0, newCourses, 0, getCourses().length);
        newCourses[newCourses.length - 1] = course;

        this.setCourses(newCourses);
    }

    public void evaluateTask(int taskID, String courseName) {
        Course courseToEvaluate = null;
        for (Course course : getCourses()) {
            if (course.getName().equals(courseName)) {
                courseToEvaluate = course;
            }
        }
        if (courseToEvaluate == null) throw new IllegalArgumentException("El curso no existe o este profesor no puede acceder a él");

        Task taskToEvaluate = null;
        for (Task task : courseToEvaluate.getTasks()) {
            if (task.getId() == taskID) {
                taskToEvaluate = task;
            }
        }
        if (taskToEvaluate == null) throw new IllegalArgumentException("La tarea no existe o este profesor no puede acceder a ella");

        taskToEvaluate.getTasks().forEach(task -> task.setGrade(task.isDone() ? (float) Math.random() * 10 : 0.0f));

    }

    public void addTask(@NotNull Task task, @NotNull String courseName) {
        Course courseToAdd = null;
        for (Course course : getCourses()) {
            if (course.getName().equals(courseName)) {
                courseToAdd = course;
            }
        }

        if (courseToAdd == null) throw new IllegalArgumentException("El curso no existe o este profesor no puede acceder a él");

        courseToAdd.addTask(task);
    }

    public void deleteTask(int id) {
        ArrayList<Task> taskList;
        Task taskToRemove = null;
        for (Course course : getCourses()) {
            taskList = course.getTasks();
            for (Task task : taskList) {
                if (task.getId() == id) {
                    taskToRemove = task;
                }
            }
            if (taskToRemove != null) {
                taskList.remove(taskToRemove);
            }
            taskToRemove = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder cursos = new StringBuilder();
        Arrays.stream(getCourses()).forEach(course -> cursos.append(course.getName()).append(" "));
        return super.toString() + " - " + getSalary() + "€ - " + (cursos.isEmpty() ? "No tiene cursos" : cursos.toString());
    }

    @Override
    public void sobornar(Student student) {
        for (Task.StudentTask studentTask : student.getTasks()) {
            if (studentTask.getTask().getCourse().getProfessor().equals(this)) {
                studentTask.setGrade(studentTask.getGrade() + 1);
            }
        }
    }
}
