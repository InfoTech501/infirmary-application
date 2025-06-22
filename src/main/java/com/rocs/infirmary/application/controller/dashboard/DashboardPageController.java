package com.rocs.infirmary.application.controller.dashboard;

import com.rocs.infirmary.application.DashboardInfoApplication;
import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;
import com.rocs.infirmary.application.service.dashboard.DashboardDataService;
import com.rocs.infirmary.application.service.dashboard.DateRange;
import com.rocs.infirmary.application.service.dashboard.TableColumnHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DashboardPageController implements Initializable {

    private static final String GRADE_11 = "Grade 11";
    private static final String GRADE_12 = "Grade 12";
    private static final String DATE_FORMAT = "MMMMM dd, yyyy";
    private static final LocalDate DEFAULT_START_DATE = LocalDate.of(2000, 1, 1);

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

    private DashboardDataService dataService;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeServices();
        initializeUI();
        loadDashboardData();
    }

    private void initializeServices() {
        DashboardInfoApplication dashboardInfoApplication = new DashboardInfoApplication();
        dashboardFacade = dashboardInfoApplication.getDashboardFacade();
        dataService = new DashboardDataService(dashboardFacade);
    }

    private void initializeUI() {
        setDateDisplay();
        initializeTableColumns();
    }

    private void setDateDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String datenow = sdf.format(new Date());
        dateDisplay.setText(datenow);
    }

    private void loadDashboardData() {
        try {
            DateRange dateRange = DateRange.daily();

            setClinicVisitReports(dateRange);
            setMedicationDistributionReport(dateRange);
            populateCharts();
            populateTables();

        } catch (Exception e) {
            showErrorMessage("Failed to load dashboard data: " + e.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        System.err.println("Dashboard Error: " + message);
    }

    private void populateCharts() {
        DateRange dateRange = DateRange.weekly();
        try {
            studentVisitBarChart.getData().clear();
            studentVisitBarChart.getYAxis().setLabel("Visits");
            initializeBarChartWeeklyVisitByGrade(dateRange, GRADE_11);
            initializeBarChartWeeklyVisitByGrade(dateRange, GRADE_12);
        } catch (Exception e) {
            System.err.println("Failed to populate charts: " + e.getMessage());
        }
    }

    private void populateTables() {
        DateRange dateRange = DateRange.monthly();
        try {
            populateTableMedicationTrendReport(dateRange);
            populateTableCommonAilmentsReport(dateRange);
        } catch (Exception e) {
            System.err.println("Failed to populate tables: " + e.getMessage());
        }
    }

    private void populateTableMedicationTrendReport(DateRange dateRange) {
        List<MedicationTrendReport> medicationTrendReports =
                dashboardFacade.generateMedicationReport(dateRange.getStartDate(), dateRange.getEndDate());
        ObservableList<MedicationTrendReport> dataMedTrend =
                FXCollections.observableArrayList(medicationTrendReports);
        medTrendRptTable.setItems(dataMedTrend);
    }

    private void populateTableCommonAilmentsReport(DateRange dateRange) {
        String gradeLevel = "";
        String section = "";

        List<CommonAilmentsReport> reports = dashboardFacade.generateCommonAilmentReport(
                dateRange.getStartDate(), dateRange.getEndDate(), gradeLevel, section);
        ObservableList<CommonAilmentsReport> observableCommonAilmentTable =
                FXCollections.observableArrayList(reports);
        commonAilmentsRptTable.setItems(observableCommonAilmentTable);
    }

    private void setClinicVisitReports(DateRange dateRange) {
        int grade11Visits = dataService.getVisitCount(dateRange, GRADE_11);
        int grade12Visits = dataService.getVisitCount(dateRange, GRADE_12);

        grade11ClinicVisitTodayRprt.setText(String.valueOf(grade11Visits));
        grade12ClinicVisitTodayRprt.setText(String.valueOf(grade12Visits));
    }

    private void setMedicationDistributionReport(DateRange dateRange) {
        int totalUsage = dataService.getTotalMedicationUsage(dateRange);
        medDistributtedTodayRprt.setText(String.valueOf(totalUsage));
    }

    private void initializeBarChartWeeklyVisitByGrade(DateRange dateRange, String gradeLevel) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEEE");
        List<FrequentVisitReport> reports = dashboardFacade.generateFrequentVisitReport(
                dateRange.getStartDate(), dateRange.getEndDate(), gradeLevel);
        Map<String, Integer> visitsPerDay = new HashMap<>();

        for (FrequentVisitReport report : reports) {
            String day = sdf.format(report.getVisitDate());
            visitsPerDay.merge(day, report.getVisitCount(), Integer::sum);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(gradeLevel);
        List<String> orderedDays = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

        for (String day : orderedDays) {
            int visits = visitsPerDay.getOrDefault(day, 0);
            series.getData().add(new XYChart.Data<>(day, visits));
        }
        studentVisitBarChart.getData().add(series);
    }

    private void initializeTableColumns() {
        TableColumnHelper.setupNumberedColumn(numberedColumnMedTrend);
        TableColumnHelper.setupCenteredColumn(medicineColumnMedTrend,
                MedicationTrendReport::getMedicineName);
        TableColumnHelper.setupCenteredColumn(totalDistributedMedTrend,
                reports -> String.valueOf(reports.getUsage()));

        TableColumnHelper.setupNumberedColumn(numberedColumnCommonAilment);
        TableColumnHelper.setupCenteredColumn(illnessColumnCommonAilment,
                CommonAilmentsReport::getAilment);
        TableColumnHelper.setupCenteredColumn(numOfStudCommonAilment,
                report -> String.valueOf(report.getOccurrences()));
    }
}