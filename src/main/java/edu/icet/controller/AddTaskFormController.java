package edu.icet.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

public class AddTaskFormController {


    @FXML
    private DatePicker dateDueDate;

    @FXML
    private JFXTextArea txaDescription;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    void btnAddTodoOnAction(ActionEvent event) {
        Task task =new Task(
                txtTitle.getText(),
                txaDescription.getText(),
                dateDueDate.getValue()
        );
        txtTitle.setText(null);
        txaDescription.setText(null);
        dateDueDate.setValue(null);

        boolean b = MainFormController.service.addTask(task);
        if (b){
            new Alert(Alert.AlertType.INFORMATION,"Task Added Successfully ! ").show();
        }

    }

}
