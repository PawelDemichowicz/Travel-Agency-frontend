package com.project.travelfrontend.service;

import com.project.travelfrontend.domain.Task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class TaskService {

    private Set<Task> tasks;
    private static TaskService taskService;

    private TaskService(){
        this.tasks = exampleData();
    }

    public static TaskService getInstance(){
        if (taskService == null){
            taskService = new TaskService();
        }
        return taskService;
    }

    public Set<Task> getTasks(){
        return new HashSet<>(tasks);
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public String countDays(Task task){
        String days = Long.toString(ChronoUnit.DAYS.between(task.getCreatedDate(),task.getDeadlineDate()));
        return days;
    }

    private Set<Task> exampleData(){
        Set<Task> tasks = new HashSet<>();
        tasks.add(new Task(1L,"task1", "description1", LocalDate.of(2019,11,16)));
        tasks.add(new Task(2L,"task2", "description2", LocalDate.of(2019,12,17)));
        tasks.add(new Task(3L,"task3", "description3", LocalDate.of(2019,11,18)));
        tasks.add(new Task(4L,"task3", "description3", LocalDate.of(2019,11,18)));
        tasks.add(new Task(5L,"task3", "description3", LocalDate.of(2019,11,18)));
        tasks.add(new Task(6L,"task3", "description3", LocalDate.of(2019,11,18)));
        tasks.add(new Task(7L,"task3", "description3", LocalDate.of(2019,11,18)));
        tasks.add(new Task(8L,"task3", "description3", LocalDate.of(2019,11,18)));
        tasks.add(new Task(9L,"task3", "description3", LocalDate.of(2019,11,18)));
        tasks.add(new Task(10L,"task3", "description3", LocalDate.of(2019,11,18)));
        tasks.add(new Task(11L,"task3", "description3", LocalDate.of(2019,11,18)));
        return tasks;
    }


}
