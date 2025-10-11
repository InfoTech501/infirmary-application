package com.rocs.infirmary.application.controller.dashboard;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;
import java.io.IOException;



@ExtendWith(ApplicationExtension.class)
class DashboardPageControllerTest {

    private BarChart studentVisitBarChart;
    private TableView<CommonAilmentsReport> commonAilmentsRptTable;
    private TableView<MedicationTrendReport> medTrendRptTable;
    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/DashboardPage.fxml"));
        BorderPane mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setup(FxRobot robot){
        studentVisitBarChart = robot.lookup("#studentVisitBarChart").queryAs(BarChart.class);
        commonAilmentsRptTable =robot.lookup("#commonAilmentsRptTable").queryAs(TableView.class);
        medTrendRptTable = robot.lookup("#medTrendRptTable").queryAs(TableView.class);

        assertNotNull(studentVisitBarChart);
        assertNotNull(commonAilmentsRptTable);
        assertNotNull(medTrendRptTable);
    }


    @Disabled
    @Test
    void testStudentVisitBarChart(FxRobot robot){
        robot.clickOn("#weeklyStudentVisitReport");
        robot.clickOn("#monthlyStudentVisitReport");
        robot.clickOn("#yearlyStudentVisitReport");
        assertNotNull(studentVisitBarChart.getData());
        assertFalse(studentVisitBarChart.getData().isEmpty());
    }

    @Disabled
    @Test
    void testCommonAilmentsRptTable(){

        CommonAilmentsReport mockReport = mock(CommonAilmentsReport.class);
        when(mockReport.getAilment()).thenReturn("toothache");
        commonAilmentsRptTable.getItems().add(mockReport);
        assertTrue(commonAilmentsRptTable.getItems().stream().anyMatch(ailment -> "toothache".equals(ailment.getAilment())));
        assertFalse(commonAilmentsRptTable.getItems().isEmpty());

    }

    @Disabled
    @Test
    void testMedTrendRptTable(){

        MedicationTrendReport mockMedTrend = mock(MedicationTrendReport.class);
        when(mockMedTrend.getMedicineName()).thenReturn("acetaminophen");
        medTrendRptTable.getItems().add(mockMedTrend);
        assertTrue(medTrendRptTable.getItems().stream().anyMatch(med -> "acetaminophen".equals(med.getMedicineName())));
        assertFalse(medTrendRptTable.getItems().isEmpty());

    }

}





