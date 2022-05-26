package com.fadedbytes.colegio.api;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Course {

    private static final List<Course> COURSES = new ArrayList<>();

    private final String name;
    private Professor professor;
    private List<Student> students;
    private float courseAverage;

    public Course(@NotNull String name, @NotNull Professor professor) {
        this.name = name;
        this.professor = professor;
        this.setProfessor(professor);
        this.students = new ArrayList<>();

        COURSES.add(this);
    }

    public Course(@NotNull String name, @NotNull Professor professor, @NotNull List<Student> students) {
        this(name, professor);
        this.students.addAll(students);
    }

    public static Course byName(String name) {
        for (Course course : COURSES) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }

    public static List<Course> getAllCourses() {
        return COURSES;
    }

    public void setProfessor(Professor newProfessor) {
        ArrayList<Course> newCourseList = new ArrayList<>(List.of(this.professor.getCourses()));
        newCourseList.remove(this);
        this.professor.setCourses(newCourseList.toArray(new Course[0]));

        ArrayList<Course> newProfessorList = new ArrayList<>(List.of(newProfessor.getCourses()));
        newProfessorList.add(this);
        newProfessor.setCourses(newProfessorList.toArray(new Course[0]));

        this.professor = newProfessor;
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
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : Task.getAllTasks()) {
            if (task.getCourse().equals(this)) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void addTask(Task task) {
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
