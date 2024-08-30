package edu.icet.controller;

import edu.icet.model.Task;
import javafx.collections.ObservableList;

public interface TaskService {
    boolean addTask(Task task);
    ObservableList<Task> getAll();
    ObservableList<Task> getAllCom();
    boolean completeTask(int id);
    boolean addTaskCom(Task task);


}
