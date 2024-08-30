package edu.icet.controller;

import edu.icet.db.DBConnection;
import edu.icet.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class TaskController implements TaskService{
    @Override
    public boolean addTask(Task task) {
        try {
            String SQL = "INSERT INTO active_tasks (title, description, completion_date) VALUES (?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,task.getTitle());
            psTm.setObject(2,task.getDescription());
            psTm.setDate(3, Date.valueOf(task.getDate()));
            System.out.println(psTm.executeUpdate());
            return  psTm.executeUpdate()>0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Error" + e.getMessage()).show();
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Task> getAll() {
        ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM active_tasks";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()){
                //System.out.println(resultSet.getString("CustTitle")+resultSet.getString("CustName"));
                Task task =new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("completion_date").toLocalDate()

                );
                taskObservableList.add(task);

            }
            return  taskObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Task> getAllCom() {
        ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM completed_tasks";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()){
                Task task =new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("completion_date").toLocalDate()

                );
                taskObservableList.add(task);

            }
            return  taskObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean completeTask(int id) {
        try {
            String SQL="DELETE FROM active_tasks WHERE id='"+id+"'";
            Connection connection=DBConnection.getInstance().getConnection();
            return   connection.createStatement().executeUpdate(SQL)>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean addTaskCom(Task task){
        try {
            String SQL = "INSERT INTO completed_tasks (title, description, completion_date) VALUES (?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,task.getTitle());
            psTm.setObject(2,task.getDescription());
            psTm.setDate(3, Date.valueOf(task.getDate()));
            System.out.println(psTm.executeUpdate());
            return psTm.executeUpdate() >0 ;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Error" + e.getMessage()).show();
            throw new RuntimeException(e);
        }

    }
}
