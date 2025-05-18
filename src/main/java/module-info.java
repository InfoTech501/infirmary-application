module com.rocs.infirmaryapplication {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires org.slf4j;
    requires org.apache.logging.log4j.slf4j2.impl;
    requires com.oracle.database.jdbc;
    requires java.desktop;

    opens com.rocs.infirmary.application.controller.inventory to javafx.fxml;
    opens com.rocs.infirmary.application to javafx.fxml;
    opens com.rocs.infirmary.application.data.model.inventory.medicine to javafx.base;

    exports com.rocs.infirmary.application;
    exports com.rocs.infirmary.application.data.model.inventory;
    exports com.rocs.infirmary.application.data.model.inventory.medicine;
}