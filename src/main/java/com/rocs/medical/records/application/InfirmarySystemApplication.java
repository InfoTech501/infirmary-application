package com.rocs.medical.records.application;

import com.rocs.medical.records.application.app.facade.commonAilmentsReport.CommonAilmentsReportFacade;
import com.rocs.medical.records.application.app.facade.commonAilmentsReport.impl.CommonAilmentsReportFacadeImpl;
import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;
import com.rocs.medical.records.application.model.person.Person;

import com.rocs.medical.records.application.app.facade.reportMedicationTrend.ReportMedicationTrendFacade;
import com.rocs.medical.records.application.app.facade.reportMedicationTrend.impl.ReportMedicationTrendFacadeImpl;
import com.rocs.medical.records.application.model.report.MedicationTrendReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InfirmarySystemApplication {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Infirmary System Application");
        System.out.println("Please select which report:");
        System.out.println("1 - Common Ailments Report");
        System.out.println("2 - Medication Trend Report");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        switch (choice){
            case 1: {
                CommonAilmentsReportFacade ailmentsReportFacade = new CommonAilmentsReportFacadeImpl();

                try{
                    System.out.println("Common Ailments Report");

                    Date startDate = getValidInputDate(scanner, dateFormat, "Enter start date (yyyy-MM-dd): ");
                    Date endDate = getValidInputDate(scanner, dateFormat, "Enter end date (yyyy-MM-dd): ");

                    System.out.print("Enter grade level (enter to skip): ");
                    String gradeLevel = scanner.nextLine().trim();
                    gradeLevel = gradeLevel.isEmpty() ? null : gradeLevel;

                    System.out.print("Enter section (enter to skip): ");
                    String section = scanner.nextLine().trim();
                    section = section.isEmpty() ? null : section;

                    List<CommonAilmentsReport> reports = ailmentsReportFacade.generateReport(startDate, endDate, gradeLevel, section);
                    ailmentsReportFacade.displayCommonAilmentsReport(reports, startDate, endDate, gradeLevel, section);
                } catch (RuntimeException e) {
                    System.err.println("Report generation failed: " + e.getMessage());
                }
                break;
            }

            case 2: {
                ReportMedicationTrendFacade medicationTrendFacade = new ReportMedicationTrendFacadeImpl();

                try{
                    System.out.println("\nWelcome to Medication Trend Report");

                    Date startDate = getValidInputDate(scanner, dateFormat, "Please enter start date (yyyy-MM-dd): ");
                    Date endDate = getValidInputDate(scanner, dateFormat, "Please enter end date (yyyy-MM-dd): ");

                    List<MedicationTrendReport> reports = medicationTrendFacade.generateReport(startDate, endDate);
                    medicationTrendFacade.displayMedTrendReport(reports, startDate, endDate);


                } catch (RuntimeException e) {
                    System.err.println("Error generating: " + e.getMessage());
                }
                break;
            }
        }


    }

    private static Date getValidInputDate(Scanner scanner, SimpleDateFormat dateFormat, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                Date date = dateFormat.parse(input);

                if (date.after(new Date())) {
                    System.err.println("Please enter a present or past date.");
                    continue;
                }
                return date;
            } catch (ParseException e) {
                System.err.println("Invalid date format, use yyyy-MM-dd.");
            }
        }
    }
}