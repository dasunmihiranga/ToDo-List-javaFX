package edu.icet.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MainFormController implements Initializable {
    public static TaskService service =new TaskController();

    @FXML
    private DatePicker dateDueDate;

    @FXML
    private JFXListView<String> listActivetodo;

    @FXML
    private JFXListView<String> listCompletedtodo;

    @FXML
    private JFXTextArea txaDescription;

    @FXML
    private JFXTextField txtTitle;

    private int selectedTaskId = -1; // Stores the actual task ID

    ObservableList<Task> taskList;
    ObservableList<String> titleList;
    ObservableList<Task> taskListCom;
    ObservableList<String> titleListCom;
    int count=0;
    int i =0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
        loadListCom();

        listActivetodo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // When the selection changes, this code will run
            setTextToValues(newValue);
        });
    }
    private void setTextToValues(String title) {
        taskList = service.getAll();
        for (Task task : taskList) {
            if(task.getTitle().equals(title)){
                txtTitle.setText(task.getTitle());
                txaDescription.setText(task.getDescription());
                dateDueDate.setValue(task.getDate());
                break;
            }
        }



    }
    private void loadList() {
        taskList = service.getAll();
        titleList = FXCollections.observableArrayList();
        for (Task task : taskList) {
            titleList.add(task.getTitle());
            System.out.println(task);
        }
        listActivetodo.setItems(titleList);
    }
    private void loadListCom() {
        taskList = service.getAllCom();
        titleList = FXCollections.observableArrayList();
        for (Task task : taskList) {
            titleList.add(task.getTitle());
        }
        listCompletedtodo.setItems(titleList);
    }

    @FXML
    void btnAddTaskOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add_task_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadList();
        loadListCom();

    }

    @FXML
    void btnTaskCompletedOnAction(ActionEvent event) {
        Task task =new Task(
                txtTitle.getText(),
                txaDescription.getText(),
                dateDueDate.getValue()
        );
        boolean b = service.addTaskCom(task);
        if (b){
            new Alert(Alert.AlertType.INFORMATION,"Task Completed Successfully ! ").show();
        }
        int id=0;
        taskList = service.getAllCom();
        titleList = FXCollections.observableArrayList();
        for (Task t : taskList) {
            if(t.getTitle().equals(txtTitle.getText())){
                id= t.getId();
                break;
            }
        }
        boolean isDeleted = service.completeTask(id);


    }


}
