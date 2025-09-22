package com.rocs.infirmary.application.controller.dashboard;

import com.rocs.infirmary.application.module.dashboard.information.application.DashboardInfoApplication;
import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;
import com.rocs.infirmary.application.service.dashboard.DateRange;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.function.Function;
/**
 * {@code DashboardController} is used to handle event processes for the Dashboard,
 * this implements Initializable interface
 **/
public class DashboardPageController implements Initializable {
    private static final List<String> WEEKLY_CATEGORIES = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
    private static final List<String> MONTHLY_CATEGORIES = List.of("Week 1", "Week 2", "Week 3", "Week 4", "Week 5");
    private static final List<String> YEARLY_CATEGORIES = List.of("June", "July", "August", "September", "October", "November",
            "December", "January", "February", "March");
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardPageController.class);
    private final DashboardInfoApplication dashboardInfoApplication = new DashboardInfoApplication();
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
    private Button weeklyStudentVisitReport;
    @FXML
    private Button monthlyStudentVisitReport;
    @FXML
    private Button yearlyStudentVisitReport;
    @FXML
    private BarChart<String, Number> studentVisitBarChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.info("Initializing Dashboard Controller");
        initializeUI();
        loadDashboardData();
        weeklyStudentVisitReport.setOnAction(event -> populateCharts("weekly"));
        monthlyStudentVisitReport.setOnAction(event -> populateCharts("monthly"));
        yearlyStudentVisitReport.setOnAction(event -> populateCharts("yearly"));
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
        LOGGER.info("Dashboard data loading");
        try {
            DateRange dateRange = DateRange.daily();

            setClinicVisitReports(dateRange);
            setMedicationDistributionReport(dateRange);
            populateCharts("weekly");
            populateTables();

            LOGGER.info("Dashboard data successfully loaded");
        } catch (IllegalStateException e) {
            LOGGER.error("Failed to load dashboard data", e);
        }
    }


    private void populateCharts(String view) {
        String GRADE_11 = "Grade 11";
        String GRADE_12 = "Grade 12";

        DateRange dateRange;
        switch (view) {
            case "weekly": dateRange = DateRange.weekly(); break;
            case "monthly": dateRange = DateRange.monthly(); break;
            case "yearly": dateRange = DateRange.yearly(); break;
            default:
                throw new IllegalArgumentException("Invalid view: " + view);
        }

        LOGGER.info("Populating Charts");
        try {
            studentVisitBarChart.getData().clear();
            studentVisitBarChart.getYAxis().setLabel("Visits");
            initializeBarChartVisitByGrade(dateRange, GRADE_11, view);
            initializeBarChartVisitByGrade(dateRange, GRADE_12, view);

            LOGGER.info("Charts successfully populated");
        } catch (NullPointerException e) {
            LOGGER.error("Failed to populate charts", e);
        }
    }

    private void populateTables() throws IllegalStateException {
        LOGGER.info("Populating Tables");
        DateRange dateRange = DateRange.monthly();
        populateTableMedicationTrendReport(dateRange);
        populateTableCommonAilmentsReport(dateRange);

        if (medTrendRptTable == null || totalDistributedMedTrend == null) {
            throw new IllegalStateException("Medication Trend Report table is null");
        }

        if (commonAilmentsRptTable == null || numOfStudCommonAilment == null) {
            throw new IllegalStateException("Common Ailments Report table is null");
        }

        if (dashboardInfoApplication.getDashboardFacade() == null) {
            throw new IllegalStateException("Dashboard Facade is null");
        }

        LOGGER.info("Tables successfully populated");
    }


    private void populateTableMedicationTrendReport(DateRange dateRange) {
        List<MedicationTrendReport> medicationTrendReports =
                dashboardInfoApplication.getDashboardFacade().generateMedicationReport(dateRange.getStartDate(), dateRange.getEndDate());
        LOGGER.debug("MedicationTrendReport from {} - {}: {}",
                dateRange.getStartDate(), dateRange.getEndDate(), medicationTrendReports);

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

        List<CommonAilmentsReport> reports = dashboardInfoApplication.getDashboardFacade().generateCommonAilmentReport(
                dateRange.getStartDate(), dateRange.getEndDate(), gradeLevel, section);
        LOGGER.debug("Common Ailments Report from {} - {}, gradeLevel='{}', section='{}': {}",
                dateRange.getStartDate(), dateRange.getEndDate(), gradeLevel, section, reports);


        ObservableList<CommonAilmentsReport> observableCommonAilmentTable =
                FXCollections.observableArrayList(reports);
        commonAilmentsRptTable.setItems(observableCommonAilmentTable);
        numOfStudCommonAilment.setSortable(true);
        numOfStudCommonAilment.setSortType(TableColumn.SortType.DESCENDING);
        commonAilmentsRptTable.getSortOrder().setAll(numOfStudCommonAilment);
        commonAilmentsRptTable.sort();
    }

    private int getVisitCount (DateRange dateRange, String gradeLevel) {
        List<FrequentVisitReport> reports = dashboardInfoApplication.getDashboardFacade().generateFrequentVisitReport(
                dateRange.getStartDate(), dateRange.getEndDate(), gradeLevel);
        return reports.size();
    }

    private void setClinicVisitReports(DateRange dateRange) {
        LOGGER.info("Setting Clinic Visit Reports Started");

        if (dateRange == null) {
            LOGGER.error("Date Range is null");
            return;
        }

        if (grade11ClinicVisitTodayRprt == null || grade12ClinicVisitTodayRprt == null) {
            LOGGER.error("Setting Clinic Visit Reports not initialized");
            return;
        }

        String GRADE_11 = "Grade 11";
        String GRADE_12 = "Grade 12";

        int grade11Visits = getVisitCount(dateRange, GRADE_11);
        int grade12Visits = getVisitCount(dateRange, GRADE_12);

        LOGGER.debug("Clinic visit reports set: Grade 11 = {}, Grade 12 = {}",
                grade11Visits, grade12Visits);

        grade11ClinicVisitTodayRprt.setText(String.valueOf(grade11Visits));
        grade12ClinicVisitTodayRprt.setText(String.valueOf(grade12Visits));

        LOGGER.info("Setting Clinic Visit Reports Finished");
    }

    private int getTotalMedicationUsage(DateRange dateRange) {
        List<MedicationTrendReport> reports = dashboardInfoApplication.getDashboardFacade().generateMedicationReport(
                dateRange.getStartDate(), dateRange.getEndDate());
        return reports.stream().mapToInt(MedicationTrendReport::getUsage).sum();
    }

    private void setMedicationDistributionReport(DateRange dateRange) {
        int totalUsage = getTotalMedicationUsage(dateRange);
        LOGGER.debug("Medication distribution report from {} to {}: total usage = {}",
                dateRange.getStartDate(), dateRange.getEndDate(), totalUsage);

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

    private void initializeBarChartVisitByGrade(DateRange dateRange, String gradeLevel, String view) {
        List<String> orderedCategories;
        Map<String, Integer> visitsPerCategory = new HashMap<>();
        List<FrequentVisitReport> reports = dashboardInfoApplication.getDashboardFacade()
                .generateFrequentVisitReport(dateRange.getStartDate(), dateRange.getEndDate(), gradeLevel);

        if (view.equals("weekly")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");
            orderedCategories = WEEKLY_CATEGORIES;
            for (FrequentVisitReport report : reports) {
                LocalDate date = ((java.sql.Date) report.getVisitDate()).toLocalDate();
                String key = date.format(formatter);
                visitsPerCategory.merge(key, report.getVisitCount(), Integer::sum);
            }
        } else if (view.equals("monthly")) {
            orderedCategories = MONTHLY_CATEGORIES;
            for (FrequentVisitReport report : reports) {
                LocalDate date = ((java.sql.Date) report.getVisitDate()).toLocalDate();
                int weekOfMonth = date.get(ChronoField.ALIGNED_WEEK_OF_MONTH);
                String key = "Week " + weekOfMonth;
                visitsPerCategory.merge(key, report.getVisitCount(), Integer::sum);
            }
        } else if (view.equals("yearly")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
            orderedCategories = YEARLY_CATEGORIES;
            for (FrequentVisitReport report : reports) {
                LocalDate date = ((java.sql.Date) report.getVisitDate()).toLocalDate();
                String key = date.format(formatter);
                visitsPerCategory.merge(key, report.getVisitCount(), Integer::sum);
            }
        } else {
            throw new IllegalArgumentException("Invalid view categories: " + view);
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(gradeLevel);
        NumberAxis yAxis = (NumberAxis) studentVisitBarChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setForceZeroInRange(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);

        CategoryAxis xAxis = (CategoryAxis) studentVisitBarChart.getXAxis();
        xAxis.setCategories(FXCollections.observableArrayList(orderedCategories));
        xAxis.setAutoRanging(true);
        xAxis.setAnimated(false);

        for (String category : orderedCategories) {
            int visits = visitsPerCategory.getOrDefault(category, 0);
            series.getData().add(new XYChart.Data<>(category, visits));
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