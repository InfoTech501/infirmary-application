package com.rocs.medical.records.application;

import com.rocs.medical.records.application.app.facade.commonAilmentsReport.CommonAilmentsReportFacade;
import com.rocs.medical.records.application.app.facade.commonAilmentsReport.impl.CommonAilmentsReportFacadeImpl;
import com.rocs.medical.records.application.app.facade.frequentVisitReport.FrequentVisitReportFacade;
import com.rocs.medical.records.application.app.facade.frequentVisitReport.impl.FrequentVisitReportFacadeImpl;
import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;
import com.rocs.medical.records.application.model.person.Person;
import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InfirmarySystemApplication {
    public static void main(String[] args) {
        CommonAilmentsReportFacade ailmentsReportFacade = new CommonAilmentsReportFacadeImpl();
        FrequentVisitReportFacade frequentVisitReportFacade = new FrequentVisitReportFacadeImpl();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Select Report Type:");
            System.out.println("1. Common Ailments Report");
            System.out.println("2. Frequent Visit Report");

            int reportType = Integer.parseInt(scanner.nextLine().trim());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            switch (reportType) {
                case 1:
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
                    displayCommonAilmentsReport(reports, startDate, endDate, gradeLevel, section);
                    break;

                case 2:
                    System.out.println("Frequent Visit Report");

                    Date frequentVisitStartDate = getValidInputDate(scanner, dateFormat, "Enter start date (yyyy-MM-dd): ");
                    Date frequentVisitEndDate = getValidInputDate(scanner, dateFormat, "Enter end date (yyyy-MM-dd): ");
                    System.out.print("Enter grade level for Frequent Visit: ");
                    String frequentVisitGradeLevel = scanner.nextLine().trim();

                    handleFrequentVisit(frequentVisitStartDate, frequentVisitEndDate, frequentVisitGradeLevel, frequentVisitReportFacade);
                    break;

                default:
                    System.out.println("Invalid report type selected.");
            }

        } catch (RuntimeException e) {
            System.err.println("Report generation failed: " + e.getMessage());
        }
    }

    private static void displayCommonAilmentsReport(List<CommonAilmentsReport> reports, Date startDate, Date endDate, String gradeLevel, String section) {
        if (reports == null || reports.isEmpty()) {
            System.out.println("No data available for the selected criteria.");
            return;
        }

        SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy");
        System.out.println("Common Ailments Report");
        System.out.println("Period: " + displayFormat.format(startDate) + " to " + displayFormat.format(endDate));
        System.out.println("Grade level: " + (gradeLevel != null ? gradeLevel : "ALL"));
        System.out.println("Section: " + (section != null ? section : "ALL"));

        for (CommonAilmentsReport report : reports) {
            printAilmentSection(report);
        }

        System.out.println("\nReport Summary");
        System.out.println("Total no. of different ailments: " + reports.size());

        int totalOccurrences = reports.stream().mapToInt(CommonAilmentsReport::getOccurrences).sum();
        System.out.println("Total no. of occurrences: " + totalOccurrences);
    }

    private static void printAilmentSection(CommonAilmentsReport report) {
        System.out.println("\nAffected students:");
        for (Person student : report.getAffectedPeople()) {
            System.out.println(student.getFirstName() + " " + student.getLastName());
        }

        System.out.println("Ailment: " + report.getAilment());
        System.out.println("No. of occurrences per ailment: " + report.getOccurrences());
        System.out.println("Grade Level: " + report.getGradeLevel());
        System.out.println("Strand: " + report.getStrand());
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

    private static void handleFrequentVisit(Date startDate, Date endDate, String gradeLevel, FrequentVisitReportFacade frequentVisitReportFacade) {
        List<FrequentVisitReport> reports = frequentVisitReportFacade.generateReport(startDate, endDate, gradeLevel);

        System.out.println("\nFrequent Visit Data:");
        System.out.println("Period: " + startDate + " to " + endDate);
        System.out.println("Grade Level: " + gradeLevel);

        if (reports == null || reports.isEmpty()) {
            System.out.println("No data available for the selected criteria.");
            return;
        }

        for (FrequentVisitReport report : reports) {
            System.out.println("Student Id: " + report.getStudentId());
            System.out.println("Visit Date: " + report.getVisitDate());
            System.out.println("Grade Level: " + report.getGradeLevel());
            System.out.println("Health Concern: " + report.getSymptoms());
        }
    }
}
