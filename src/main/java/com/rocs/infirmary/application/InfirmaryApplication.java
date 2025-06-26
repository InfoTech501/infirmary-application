package com.rocs.infirmary.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class InfirmaryApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/views/InventoryPage.fxml"));

        BorderPane mainLayout;
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}