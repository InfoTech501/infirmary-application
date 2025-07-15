package com.rocs.infirmary.application.controller.dashboard;

import com.rocs.infirmary.application.module.dashboard.information.application.DashboardInfoApplication;
import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;
import com.rocs.infirmary.application.service.dashboard.DashboardDataService;
import com.rocs.infirmary.application.service.dashboard.DateRange;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
/**
 * {@code DashboardController} is used to handle event processes for the Dashboard,
 * this implements Initializable interface
 **/
public class DashboardPageController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(DashboardPageController.class);

    @FXML
    private Label dateDisplay;

    @FXML
    private TableView<MedicationTrendReport> medTrendRptTable;

    @FXML
    private TableColumn<MedicationTrendReport, String> numberedColumnMedTrend;

    @FXML
    private TableColumn<MedicationTrendReport, String> medicineColumnMedTrend;

    @FXML
    private TableColumn<MedicationTrendReport, String> totalDistributedMedTrend;

    @FXML
    private Label medDistributtedTodayRprt;

    @FXML
    private TableView<CommonAilmentsReport> commonAilmentsRptTable;

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
        String DATE_FORMAT = "MMMMM dd, yyyy";
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

        } catch (NullPointerException e) {
            logger.error("Failed to load dashboard data: {}", e.getMessage());
        }
    }

    private void populateCharts() {
        String GRADE_11 = "Grade 11";
        String GRADE_12 = "Grade 12";
        DateRange dateRange = DateRange.weekly();
        try {
            studentVisitBarChart.getData().clear();
            studentVisitBarChart.getYAxis().setLabel("Visits");

            initializeBarChartWeeklyVisitByGrade(dateRange, GRADE_11);
            initializeBarChartWeeklyVisitByGrade(dateRange, GRADE_12);
        } catch (NullPointerException e) {
            logger.error("Failed to populate charts: {}", e.getMessage());
        }
    }

    private void populateTables() {
        DateRange dateRange = DateRange.monthly();
        try {
            populateTableMedicationTrendReport(dateRange);
            populateTableCommonAilmentsReport(dateRange);
        } catch (NullPointerException e) {
            logger.error("Failed to populate tables: {}", e.getMessage());
        }
    }

    private void populateTableMedicationTrendReport(DateRange dateRange) {
        List<MedicationTrendReport> medicationTrendReports =
                dashboardFacade.generateMedicationReport(dateRange.getStartDate(), dateRange.getEndDate());
        ObservableList<MedicationTrendReport> dataMedTrend =
                FXCollections.observableArrayList(medicationTrendReports);
        medTrendRptTable.setItems(dataMedTrend);
        totalDistributedMedTrend.setSortable(true);
        totalDistributedMedTrend.setSortType(TableColumn.SortType.DESCENDING);
        medTrendRptTable.getSortOrder().setAll(totalDistributedMedTrend);
        medTrendRptTable.sort();
    }

    private void populateTableCommonAilmentsReport(DateRange dateRange) {
        String gradeLevel = "";
        String section = "";

        List<CommonAilmentsReport> reports = dashboardFacade.generateCommonAilmentReport(
                dateRange.getStartDate(), dateRange.getEndDate(), gradeLevel, section);
        ObservableList<CommonAilmentsReport> observableCommonAilmentTable =
                FXCollections.observableArrayList(reports);
        commonAilmentsRptTable.setItems(observableCommonAilmentTable);
        numOfStudCommonAilment.setSortable(true);
        numOfStudCommonAilment.setSortType(TableColumn.SortType.DESCENDING);
        commonAilmentsRptTable.getSortOrder().setAll(numOfStudCommonAilment);
        commonAilmentsRptTable.sort();
    }

    private void setClinicVisitReports(DateRange dateRange) {
        String GRADE_11 = "Grade 11";
        String GRADE_12 = "Grade 12";
        int grade11Visits = dataService.getVisitCount(dateRange, GRADE_11);
        int grade12Visits = dataService.getVisitCount(dateRange, GRADE_12);

        grade11ClinicVisitTodayRprt.setText(String.valueOf(grade11Visits));
        grade12ClinicVisitTodayRprt.setText(String.valueOf(grade12Visits));
    }

    private void setMedicationDistributionReport(DateRange dateRange) {
        int totalUsage = dataService.getTotalMedicationUsage(dateRange);
        medDistributtedTodayRprt.setText(String.valueOf(totalUsage));
    }

    private <T> void setupNumberedColumn(TableColumn<T, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
    }

    private <T> void setupCenteredColumn(TableColumn<T, String> column,
                                         Function<T, String> valueExtractor) {
        column.setCellValueFactory( cellData ->
                new SimpleStringProperty(valueExtractor.apply(cellData.getValue())));
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

        NumberAxis yAxis = new NumberAxis();
        yAxis.setForceZeroInRange(false);

        for (String day : orderedDays) {
            int visits = visitsPerDay.getOrDefault(day, 0);
            series.getData().add(new XYChart.Data<>(day, visits));
        }
        studentVisitBarChart.getData().add(series);
    }

    private void initializeTableColumns() {
        setupNumberedColumn(numberedColumnMedTrend);
        setupCenteredColumn(medicineColumnMedTrend,
                MedicationTrendReport::getMedicineName);
        setupCenteredColumn(totalDistributedMedTrend,
                reports -> String.valueOf(reports.getUsage()));

        setupNumberedColumn(numberedColumnCommonAilment);
        setupCenteredColumn(illnessColumnCommonAilment,
                CommonAilmentsReport::getAilment);
        setupCenteredColumn(numOfStudCommonAilment,
                report -> String.valueOf(report.getOccurrences()));
    }
}