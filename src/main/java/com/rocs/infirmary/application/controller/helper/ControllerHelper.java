package com.rocs.infirmary.application.controller.helper;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;

/**
 * the {@code ControllerHelper} is used to facilitate commonly used method
 * */
public class ControllerHelper {
    /**
     * This method creates and shows a modal dialog with a custom title and content message.
     * @param title used to set the title of the dialog
     * @param content used to set the content of the dialog
     * */
    public static void showDialog(String title,String content){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(content);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    /**
     * Displays a confirmation dialog with "Yes" and "No" buttons.
     *
     * This method creates and shows a modal dialog with a custom title and content message.
     * It allows the user to confirm or cancel an action. The response is returned as an
     * {@link Optional} containing the {@link ButtonType} selected by the user.
     *
     * @param title   the title of the alert dialog
     * @param content the content message to be displayed in the dialog
     * @return {@code Optional<ButtonType>} which will be used for the user's choice {@code ButtonType.YES} if "Yes" was clicked {@code ButtonType.NO} if "No" was clicked empty if the dialog was closed without a selection
     **/
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
