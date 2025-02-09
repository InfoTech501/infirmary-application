package com.rocs.infirmary.application;

import com.rocs.infirmary.application.app.facade.reportMedicationTrend.ReportMedicationTrendFacade;
import com.rocs.infirmary.application.app.facade.reportMedicationTrend.impl.ReportMedicationTrendFacadeImpl;
import com.rocs.infirmary.application.model.report.MedicationTrendReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InfirmarySystemApplication {
    public static void main(String[] args) {
        ReportMedicationTrendFacade medicationTrendFacade = new ReportMedicationTrendFacadeImpl();

        try (Scanner scanner = new Scanner(System.in)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            System.out.println("Welcome to Medication Trend Report");

            Date startDate = getValidDate(scanner, dateFormat, "Please enter start date (yyyy-MM-dd): ");
            Date endDate = getValidDate(scanner, dateFormat, "Please enter end date (yyyy-MM-dd): ");

            List<MedicationTrendReport> report = medicationTrendFacade.generateReport(startDate, endDate);
            displayMedTrendReport(report, startDate, endDate);

//            ReportMedicationTrendFacade repository = (startDateDate, endDateDate) -> List.of();
//            MedicationTrendReportService service = new MedicationTrendReportService(repository);
//
//            MedicationTrendReport report = service.generateReport(startingDate, endDate);
//            List<CommonAilmentsReport> reports = ailmentsReportFacade.generateReport(startDate, endDate, gradeLevel, section);
//            displayReport(reports, startDate, endDate, gradeLevel, section);

//            if (report != null) {
//                System.out.println("\nReport generated!:" );
//                System.out.println(report);
//            } else {
//                System.out.println("No data available for the selected criteria.");
//            }
        } catch (RuntimeException e) {
            System.err.println("Error generating: " + e.getMessage());
        }
    }

    private static void displayMedTrendReport(List<MedicationTrendReport> report, Date startDate, Date endDate) {
        if (report == null || report.isEmpty()) {
            System.out.println("No report");
            System.out.println("No data available for the selected criteria.");
            return;
        }

        SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy");
        System.out.println("Common Ailments report");
        System.out.println("Period date: " + displayFormat.format(startDate) + " to " + displayFormat.format(endDate));

        for (MedicationTrendReport reports : report) {
            printMedicationTrend(reports);
        }

    }

    private static void printMedicationTrend(MedicationTrendReport report) {
        System.out.println("\nMedication Usage: " + report.getQuantityUsed());
        System.out.print("  Medicine: " + report.getMedName());
        System.out.print("  Medication Stocks: " + report.getQuantityAvailable());
        }

    private static Date getValidDate(Scanner scanner, SimpleDateFormat dateFormat, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return dateFormat.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.err.println("Invalid date, please use yyyy-MM-dd.");
            }
        }
    }
}
