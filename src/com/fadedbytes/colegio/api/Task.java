package com.fadedbytes.colegio.api;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class Task {

    private static int taskNumber = 0;

    private final int ID;
    private String name;
    private String description;
    private Course course;
    private Collection<StudentTask> tasks;

    public Task(String name, String description, Course course) {
        this.ID = taskNumber;
        taskNumber ++;
        this.tasks = new ArrayList<>();

        this.name = name;
        this.description = description;
        this.course = course;
    }

    public int getId() {
        return ID;
    }

    public float getGrade() {
        float grade = 0;
        for (StudentTask task : tasks) {
            grade += task.getGrade();
        }
        return grade / tasks.size();
    }

    @NotNull
    public StudentTask get() {
        return new StudentTask(this);
    }

    public Collection<StudentTask> getTasks() {
        return new ArrayList<>(tasks);
    }

    @NotNull
    public Course getCourse() {
        return course;
    }


    static class StudentTask {
        private final Task TASK;
        private boolean done;
        private float grade;

        public StudentTask(@NotNull Task parentTask) {
            this.TASK = parentTask;
        }

        public void setGrade(float grade) {
            this.grade = grade < 0 ? 0 : grade > 10 ? 10 : grade;
        }

        public void complete() {
            this.done = true;
        }

        public boolean isDone() {
            return done;
        }

        public float getGrade() {
            return grade;
        }

        @NotNull
        public Task getTask() {
            return TASK;
        }
    }



}
