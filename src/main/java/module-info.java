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
    opens com.rocs.infirmary.application.data.model.report to javafx.fxml;
    opens com.rocs.infirmary.application.controller.dashboard to javafx.fxml;
    opens com.rocs.infirmary.application.data.model.person.student to javafx.fxml;
    opens com.rocs.infirmary.application.controller.mainpage to javafx.fxml;

    exports com.rocs.infirmary.application;
    exports com.rocs.infirmary.application.data.model.inventory;
    exports com.rocs.infirmary.application.data.model.inventory.medicine;
    exports com.rocs.infirmary.application.data.model.person.student;

    exports com.rocs.infirmary.application.module.inventory.management.application;
    opens com.rocs.infirmary.application.module.inventory.management.application to javafx.fxml;
    exports com.rocs.infirmary.application.module.medical.record.management.application;
    opens com.rocs.infirmary.application.module.medical.record.management.application to javafx.fxml;
    exports com.rocs.infirmary.application.controller.dashboard;
    exports com.rocs.infirmary.application.data.model.report;
    exports com.rocs.infirmary.application.module.dashboard.information.application;
    opens com.rocs.infirmary.application.module.dashboard.information.application to javafx.fxml;
    exports com.rocs.infirmary.application.controller.mainpage;
    exports com.rocs.infirmary.application.controller.records;
    opens com.rocs.infirmary.application.controller.records to javafx.fxml;
    exports com.rocs.infirmary.application.data.model.medicalrecord;
    opens com.rocs.infirmary.application.data.model.medicalrecord to javafx.fxml;

}