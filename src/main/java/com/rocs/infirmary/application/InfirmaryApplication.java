package com.rocs.infirmary.application;

import com.rocs.infirmary.application.app.facade.medicine.inventory.MedicineInventoryFacade;
import com.rocs.infirmary.application.app.facade.medicine.inventory.impl.MedicineInventoryFacadeImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InfirmaryApplication extends Application{
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

    public static void main(String[] args) throws ParseException {
        launch();
        MedicineInventoryFacade medicineInventoryFacade = new MedicineInventoryFacadeImpl();
        medicineInventoryFacade.updateMedicineInventory("IB",50,null, Date.valueOf("2026-01-01"));
    }
}