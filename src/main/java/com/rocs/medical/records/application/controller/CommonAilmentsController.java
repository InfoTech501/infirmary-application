package com.rocs.medical.records.application.controller;

import com.rocs.medical.records.application.app.facade.facade.commonAilmentsReport.CommonAilmentsReportFacade;
import com.rocs.medical.records.application.app.facade.facade.commonAilmentsReport.impl.CommonAilmentsReportFacadeImpl;
import com.rocs.medical.records.application.model.reportAffectedStudents.ReportAffectedStudents;
import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CommonAilmentsController {
    CommonAilmentsReportFacade ailmentsReportFacade = new CommonAilmentsReportFacadeImpl();

    public void viewCommonAilments() {
        try (Scanner scanner = new Scanner(System.in)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            System.out.println("Common Ailments Report Generation");

            Date startDate = getValidDate(scanner, dateFormat, "Enter start date (yyyy-MM-dd): ");
            Date endDate = getValidDate(scanner, dateFormat, "Enter end date (yyyy-MM-dd): ");

            System.out.print("Enter grade level (enter to skip): ");
            String gradeLevel = scanner.nextLine().trim();
            gradeLevel = gradeLevel.isEmpty() ? null : gradeLevel;

            System.out.print("Enter section (enter to skip): ");
            String section = scanner.nextLine().trim();
            section = section.isEmpty() ? null : section;



            List<CommonAilmentsReport> reports = ailmentsReportFacade.generateReport(startDate, endDate, gradeLevel, section);
            displayReport(reports, startDate, endDate, gradeLevel, section);


        } catch (RuntimeException e) {
            System.err.println("Report generation failed: " + e.getMessage());
        }
    }

    private void displayReport(List<CommonAilmentsReport> reports, Date startDate, Date endDate,
                               String gradeLevel, String section) {
        if (reports == null || reports.isEmpty()) {
            System.out.println("No reports");
            System.out.println("No data available for the specified criteria.");
            return;
        }

        SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy");
        System.out.println("Common Ailments Report");
        System.out.println("Period: " + displayFormat.format(startDate) + " to " + displayFormat.format(endDate));
        System.out.println("Grade level: " + (gradeLevel != null ? gradeLevel : "all grade levels"));
        System.out.println("Section: " + (section != null ? section : "all sections"));

        for (CommonAilmentsReport report : reports) {
            printAilmentSection(report);
        }

        System.out.println("Report summary");
        System.out.println("Total number of different ailments: " + reports.size());
        int totalOccurrences = reports.stream()
                .mapToInt(CommonAilmentsReport::getOccurrences)
                .sum();
        System.out.println("Total number of cases: " + totalOccurrences);
    }

    // not good but okay
    private void printAilmentSection(CommonAilmentsReport report) {
        System.out.println("\nAilment: " + report.getAilment());
        System.out.println("Number of Cases: " + report.getOccurrences());
        System.out.println("\nAffected Students:");

        for (ReportAffectedStudents student : report.getAffectedStudents()) {
            System.out.println("Affected Student: " + student.getFirstName() + " " + student.getLastName());
        }
    }

    private Date getValidDate(Scanner scanner, SimpleDateFormat dateFormat, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                Date date = dateFormat.parse(input);

                if (date.after(new Date())) {
                    System.err.println("Please enter a present date.");
                    continue;
                }
                return date;
            } catch (ParseException e) {
                System.err.println("Invalid date format. Please use: yyyy-MM-dd");
            }
        }
    }

//    private Date getValidDate(Scanner scanner, SimpleDateFormat dateFormat, String prompt) {
//        while (true) {
//            try {
//                System.out.print(prompt);
//                return dateFormat.parse(scanner.nextLine());
//            } catch (ParseException e) {
//                System.err.println("Invalid date format, use: yyyy-MM-dd.");
//            }
//        }
//    }

}
