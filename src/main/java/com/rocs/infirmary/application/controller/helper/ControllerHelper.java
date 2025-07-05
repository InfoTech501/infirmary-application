package com.rocs.infirmary.application.controller.helper;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;

public class ControllerHelper {
    public static void showDialog(String title,String content){
        Dialog dialog = new Dialog();
        dialog.setTitle(title);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(content);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    public static Optional<ButtonType> alertAction(String title, String content) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setContentText(content);

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(yesButton, noButton);

        return dialog.showAndWait();
    }

}
