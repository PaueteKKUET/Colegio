package com.fadedbytes.colegio.api;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Course {

    private final String name;
    private Professor professor;
    private List<Student> students;
    private float courseAverage;
    private final ArrayList<Task> tasks;

    public Course(@NotNull String name, @NotNull Professor professor) {
        this.name = name;
        this.professor = professor;
        this.students = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public Course(@NotNull String name, @NotNull Professor professor, @NotNull List<Student> students) {
        this(name, professor);
        this.students.addAll(students);
    }

    public void setProfessor(Professor professor) {
        Course[] newCourseList = (Course[]) Arrays.stream(this.professor.getCourses())
                .filter(course -> !course.equals(this))
                .toArray();

        this.professor.setCourses(newCourseList);

        this.professor = professor;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public Professor getProfessor() {
        return this.professor;
    }

    @NotNull
    public List<Student> getStudents() {
        return new ArrayList<>(this.students);
    }

    public void setStudents(@NotNull List<Student> students) {
        this.students = students;
        for (Student student : students) {
            student.addCourse(this);
        }
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.addCourse(this);
    }

    @NotNull
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(this.tasks);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        for (Student student : this.students) {
            student.addTask(task.get());
        }
    }

    public float getCourseAverage() {
        return this.courseAverage;
    }

    public void setCourseAverage(float courseAverage) {
        this.courseAverage = courseAverage;
    }

    @Override
    public String toString() {
        return this.getName() + " - " + this.getProfessor().getName() + " (media de " + this.getCourseAverage() + ")";
    }
}
