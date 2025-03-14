module com.rocs.infirmaryapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.rocs.infirmary.application to javafx.fxml;
    exports com.rocs.infirmary.application;
}