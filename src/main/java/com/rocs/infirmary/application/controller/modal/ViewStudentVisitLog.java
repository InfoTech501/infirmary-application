package com.rocs.infirmary.application.controller.modal;

import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewStudentVisitLog implements Initializable {

    @FXML
    private ToggleButton viewCloseButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleCloseButton(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void setStudentData(Student student) {
        // populate UI fields with student data
    }
}

