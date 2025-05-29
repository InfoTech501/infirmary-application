package com.rocs.infirmary.application.app.controller.page;

import com.rocs.infirmary.application.app.DashboardInfoApplication;
import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class DashboardPageController implements Initializable {

    @FXML
    private AnchorPane chartForm;

    @FXML
    private Label dateDisplay;

    @FXML
    TableView<MedicationTrendReport> medTrendRptTable;

    @FXML
    private TableColumn<MedicationTrendReport, String> numberedColumnMedTrend;

    @FXML
    private TableColumn<MedicationTrendReport, String> medicineColumnMedTrend;

    @FXML
    private TableColumn<MedicationTrendReport, String> totalDistributedMedTrend;

    @FXML
    private Label medDistributtedTodayRprt;

    @FXML
    TableView<CommonAilmentsReport> commonAilmentsRptTable;

    @FXML
    private TableColumn<CommonAilmentsReport, String> numberedColumnCommonAilment;

    @FXML
    private TableColumn<CommonAilmentsReport, String> illnessColumnCommonAilment;

    @FXML
    private TableColumn<CommonAilmentsReport, String> numOfStudCommonAilment;

    @FXML
    private Label grade11ClinicVisitTodayRprt;

    @FXML
    private Label grade12ClinicVisitTodayRprt;

    @FXML
    private Label usernameDisplay;

    @FXML
    private BarChart<String, Number> studentVisitBarChart;


    private DashboardFacade dashboardFacade;
    private DashboardInfoApplication dashboardInfoApplication;

    ObservableList<CommonAilmentsReport> observableCommonAilmentTable;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dashboardInfoApplication = new DashboardInfoApplication();
        dashboardFacade = dashboardInfoApplication.getDashboardFacade();

        setDateDisplay();
        setGrade11ClinicVisitTodayRprt();
        setGrade12ClinicVisitTodayRprt();
        setMedDistributtedTodayRprt();
        populateBarChartWklyVisit();
        initializeCommonAilmentsRptTable();
        populateTableCommonAilmentsRpt();
        initializeMedTrendRptTable();
        populateTableMedTrendRpt();
    }

    public AnchorPane getChartForm() {
        return chartForm;
    }

    private void setDateDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
        String datenow = sdf.format(new Date());
        dateDisplay.setText(datenow);
    }

    private void setGrade11ClinicVisitTodayRprt(){
        LocalDate today = LocalDate.now();
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String gradeLevel = "Grade 11";

        List<FrequentVisitReport> clinicVisitorTodayRpt = dashboardFacade.generateFrequentVisitReport(startDate, endDate, gradeLevel);
        int clinicVisitorToday = clinicVisitorTodayRpt.size();
        grade11ClinicVisitTodayRprt.setText(String.valueOf(clinicVisitorToday));

    }

    private void setGrade12ClinicVisitTodayRprt(){
        LocalDate today = LocalDate.now();
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String gradeLevel = "Grade 12";

        List<FrequentVisitReport> admittedTodayRpt = dashboardFacade.generateFrequentVisitReport(startDate, endDate, gradeLevel);
        int admittedToday = admittedTodayRpt.size();
        grade12ClinicVisitTodayRprt.setText(String.valueOf(admittedToday));
    }

    private void setMedDistributtedTodayRprt() {
        LocalDate today = LocalDate.now();
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<MedicationTrendReport> medDistributtedTodayRpt = dashboardFacade.generateMedicationReport(startDate, endDate);
            int usage = medDistributtedTodayRpt.stream().mapToInt(MedicationTrendReport::getUsage).sum();
            medDistributtedTodayRprt.setText(String.valueOf(usage));

    }

    private void populateBarChartWklyVisit() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        Date startDate = Date.from(startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());

        studentVisitBarChart.getData().clear();
        studentVisitBarChart.getXAxis().setLabel("");
        studentVisitBarChart.getYAxis().setLabel("Visits");

        SimpleDateFormat sdf = new SimpleDateFormat("EEEEE");

        List<FrequentVisitReport> grade11Reports = dashboardFacade.generateFrequentVisitReport(startDate, endDate, "Grade 11");
        List<FrequentVisitReport> grade12Reports = dashboardFacade.generateFrequentVisitReport(startDate, endDate, "Grade 12");

        Map<String, Integer> combinedVisitCounts = new HashMap<>();

        for (FrequentVisitReport report : grade11Reports) {
            String day = sdf.format(report.getVisitDate());
            combinedVisitCounts.merge(day, report.getVisitCount(), Integer::sum);
        }

        for (FrequentVisitReport report : grade12Reports) {
            String day = sdf.format(report.getVisitDate());
            combinedVisitCounts.merge(day, report.getVisitCount(), Integer::sum);
        }

        XYChart.Series<String, Number> combinedSeries = new XYChart.Series<>();
        combinedSeries.setName("Grade 11 & 12 Weekly Visits");

        List<String> orderedDays = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

        for (String day : orderedDays) {
            int visits = combinedVisitCounts.getOrDefault(day, 0);
            combinedSeries.getData().add(new XYChart.Data<>(day, visits));
        }

        studentVisitBarChart.getData().add(combinedSeries);
    }


    private void initializeCommonAilmentsRptTable() {
        numberedColumnCommonAilment.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        numberedColumnCommonAilment.setStyle("-fx-alignment: CENTER;");

        illnessColumnCommonAilment.setCellValueFactory(cellData -> {
            String illness = cellData.getValue().getAilment();
            return new SimpleStringProperty(illness);
        });

        numOfStudCommonAilment.setCellValueFactory(cellData -> {
            int numOfStudents = cellData.getValue().getOccurrences();
            return new SimpleStringProperty(String.valueOf(numOfStudents));
        });
    }

    public void populateTableCommonAilmentsRpt() {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        Date startDate = java.sql.Date.valueOf(start);
        Date endDate = java.sql.Date.valueOf(end);
        String section = "ALL";

        List<CommonAilmentsReport> grade11Reports = dashboardFacade.generateCommonAilmentReport(startDate, endDate, "Grade 11", section);
        List<CommonAilmentsReport> grade12Reports = dashboardFacade.generateCommonAilmentReport(startDate, endDate, "Grade 12", section);
        Map<String, Integer> ailmentMap = new HashMap<>();
        for (CommonAilmentsReport report : grade11Reports) {
            ailmentMap.put(report.getAilment(), report.getOccurrences());
        }

        for (CommonAilmentsReport report : grade12Reports) {
            ailmentMap.merge(report.getAilment(), report.getOccurrences(), Integer::sum);
        }

        List<CommonAilmentsReport> mergedReports = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ailmentMap.entrySet()) {
            mergedReports.add(new CommonAilmentsReport());
        }

        ObservableList<CommonAilmentsReport> observableCommonAilmentTable = FXCollections.observableArrayList(mergedReports);
        commonAilmentsRptTable.setItems(observableCommonAilmentTable);
    }

    private void initializeMedTrendRptTable() {
        numberedColumnMedTrend.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        numberedColumnMedTrend.setStyle("-fx-alignment: CENTER;");

        medicineColumnMedTrend.setCellValueFactory(cellData -> {
            String medicine = cellData.getValue().getMedicineName();
            return new SimpleStringProperty(medicine);
        });
        medicineColumnMedTrend.setStyle("-fx-alignment: CENTER;");

        totalDistributedMedTrend.setCellValueFactory(cellData -> {
            int distributed = cellData.getValue().getUsage();
            return new SimpleStringProperty(String.valueOf(distributed));
    });
        totalDistributedMedTrend.setStyle("-fx-alignment: CENTER;");
    }

    private void populateTableMedTrendRpt() {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        Date startDate = java.sql.Date.valueOf(start);
        Date endDate = java.sql.Date.valueOf(end);

        List<MedicationTrendReport> medicationTrendReports = dashboardFacade.generateMedicationReport(startDate, endDate);
        ObservableList<MedicationTrendReport> dataMedTrend = FXCollections.observableArrayList(medicationTrendReports);
        medTrendRptTable.setItems(dataMedTrend);
    }
}



