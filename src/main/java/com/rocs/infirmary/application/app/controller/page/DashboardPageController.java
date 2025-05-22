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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {
    @FXML
    private Label admittedStudTodayRprt;

    @FXML
    private AnchorPane chartForm;

    @FXML
    private Label dateDisplay;

    @FXML
    TableView<MedicationTrendReport> medTrendRptTable;

    @FXML
    private TableColumn<MedicationTrendReport, String> rankColumnMedTrend;

    @FXML
    private TableColumn<MedicationTrendReport, String> medicineColumnMedTrend;

    @FXML
    private TableColumn<MedicationTrendReport, String> totalDistributedMedTrend;

    @FXML
    private Label medDistributtedTodayRprt;

    @FXML
    TableView<CommonAilmentsReport> commonAilmentsRptTable;

    @FXML
    private TableColumn<CommonAilmentsReport, String> rankColumnCommonAilment;

    @FXML
    private TableColumn<CommonAilmentsReport, String> illnessColumnCommonAilment;

    @FXML
    private TableColumn<CommonAilmentsReport, String> numOfStudCommonAilment;

    @FXML
    private Label stillAdmittedStudTodayRprt;

    @FXML
    private Label totalClinicVisitTodayRprt;

    @FXML
    private Label usernameDisplay;

    @FXML
    private BarChart<String, Double> studentVisitBarChart;

    private DashboardFacade dashboardFacade;
    private DashboardInfoApplication dashboardInfoApplication;

    ObservableList<CommonAilmentsReport> observableCommonAilmentTable;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dashboardInfoApplication = new DashboardInfoApplication();
        dashboardFacade = dashboardInfoApplication.getDashboardFacade();

        setDateDisplay();
        setAdmittedStudTodayRprt();
        setMedDistributtedTodayRprt();
        setTotalClinicVisitTodayRprt();
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

    /**
     * This is to display the number value of Admitted Students for Today's Report
     *
     * I declared a date for while just to check if it displays the correct amount of admitted students
     * (my problem with this is it counts the size of the generated frequent visit and not the report itself,
     * but whenever
     * */
    private void setAdmittedStudTodayRprt(){
        Date startDate = new Date(2001-01-01);
        Date endDate = new Date(2025-01-01);
        String gradeLevel = "ALL";

        List<FrequentVisitReport> admittedTodayRpt = dashboardFacade.generateFrequentVisitReport(startDate, endDate, gradeLevel);
        int admittedToday = admittedTodayRpt.size();
        admittedStudTodayRprt.setText(String.valueOf(admittedToday));
    }

    /**
     * This is to display the number value of Distributed Medicine for Today's Report
     *
     * I declared a date for while just to check if it displays the correct amount of distributed medicine
     * */
    private void setMedDistributtedTodayRprt(){
        Date startDate = new Date(2001-01-01);
        Date endDate = new Date(2025-01-01);

        List<MedicationTrendReport> medDistributtedTodayRpt = dashboardFacade.generateMedicationReport(startDate, endDate);
        for (MedicationTrendReport report : medDistributtedTodayRpt) {
            int medDistributtedToday = report.getUsage();
            medDistributtedTodayRprt.setText(String.valueOf(medDistributtedToday));
        }
    }

    /**
     * This is to display the number value of the Total Admitted Students for Today's Report
     *
     * I declared a date for while just to check if it displays the correct amount of admitted students
     * */
    private void setTotalClinicVisitTodayRprt(){
        Date startDate = new Date(2001-01-01);
        Date endDate = new Date(2025-01-01);
        String gradeLevel = "ALL";

        List<FrequentVisitReport> clinicVisitorTodayRpt = dashboardFacade.generateFrequentVisitReport(startDate, endDate, gradeLevel);
        for (FrequentVisitReport report : clinicVisitorTodayRpt) {
            int clinicVisitorToday = report.getVisitCount();
            totalClinicVisitTodayRprt.setText(String.valueOf(clinicVisitorToday));
        }
    }

    /**
     * disregard this for a while since I'm still working for it.
     * */
//    private void populateBarChartWklyStudVisit() {
////        Date dateNow = new Date();
//        Date startDate = new Date(2001 - 01 - 01);
//        Date endDate = new Date(2025 - 03 - 03);
//        String gradeLevel = "";
//
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//
//        List<FrequentVisitReport> visitReport = dashboardFacade.generateFrequentVisitReport(startDate, endDate, gradeLevel);
//        CategoryAxis xAxis = new CategoryAxis();
//
//        xAxis.setCategories(FXCollections.<String>observableArrayList(String.valueOf(Arrays.asList(visitReport))));
//        xAxis.setLabel("week");
//
//
//        NumberAxis yAxis = new NumberAxis();
//        yAxis.setLabel("total");
//    }

    /**
     * This is to initialize the Common Ailments Report table
     * */
    private void initializeCommonAilmentsRptTable() {

        rankColumnCommonAilment.setCellValueFactory(cellData -> {
                int rankAilments = cellData.getTableView().getItems().size();
                return new SimpleStringProperty(String.valueOf(rankAilments));
        });

        illnessColumnCommonAilment.setCellValueFactory(new PropertyValueFactory<>("illness"));

        numOfStudCommonAilment.setCellValueFactory(cellData -> {
            int numOfStudents = cellData.getValue().getOccurrences();
            return new SimpleStringProperty(String.valueOf(numOfStudents));
        });

    }

    /**
     * This is to populate the data of the Common Ailments Report table
     *
     * I declared a date for while just to check if it displays the correct illness and the amount of the common ailments
     * */
    public void populateTableCommonAilmentsRpt() {
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date startDate = new Date(2015-01-01);
        Date endDate = new Date(2025-01-01);
        String gradeLevel = "";
        String section = "";

        List<CommonAilmentsReport> commonAilmentsReports = dashboardInfoApplication.getDashboardFacade().generateCommonAilmentReport(startDate, endDate, gradeLevel, section);
        observableCommonAilmentTable = FXCollections.observableArrayList(commonAilmentsReports);
        commonAilmentsRptTable.setItems(observableCommonAilmentTable);
    }

    /**
     * This is to initialize the Medication Trend table
     * */
    private void initializeMedTrendRptTable() {

        rankColumnMedTrend.setCellValueFactory(cellData -> {
            int rankMed = cellData.getTableView().getItems().size();
            return new SimpleStringProperty(String.valueOf(rankMed));
        });


        medicineColumnMedTrend.setCellValueFactory(cellData -> {
            String medName = cellData.getValue().getMedicineName();
            return new SimpleStringProperty(medName);
        });

        totalDistributedMedTrend.setCellValueFactory(cellData -> {
            int distributed = cellData.getValue().getUsage();
            return new SimpleStringProperty(String.valueOf(distributed));
    });
    }

    /**
     * This is to populate the data of the Medication Trend table
     *
     * I declared a date for while just to check if it displays the correct medicine and its amount that is used of the month
     * */
    private void populateTableMedTrendRpt() {
//            Date dateNow = new Date();
        Date startDate = new Date(2001 - 01 - 01);
        Date endDate = new Date(2025 - 03 - 03);

        List<MedicationTrendReport> medicationTrendReports = dashboardFacade.generateMedicationReport(startDate, endDate);
        ObservableList<MedicationTrendReport> dataMedTrend = FXCollections.observableArrayList(medicationTrendReports);
        medTrendRptTable.setItems(dataMedTrend);
    }


}



