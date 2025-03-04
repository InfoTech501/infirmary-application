package com.rocs.medical.records.application;

import com.rocs.medical.records.application.app.facade.reports.common.ailments.report.CommonAilmentsReportFacade;
import com.rocs.medical.records.application.app.facade.reports.common.ailments.report.impl.CommonAilmentsReportFacadeImpl;
import com.rocs.medical.records.application.app.facade.low.stock.medicine.LowStockMedicineFacade;
import com.rocs.medical.records.application.app.facade.low.stock.medicine.impl.LowStockMedicineFacadeImpl;
import com.rocs.medical.records.application.app.facade.medical.record.impl.StudentMedicalRecordFacadeImpl;
import com.rocs.medical.records.application.model.inventory.LowStockItem;
import com.rocs.medical.records.application.model.medical.record.StudentMedicalRecord;
import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;
import com.rocs.medical.records.application.model.person.Person;

import com.rocs.medical.records.application.app.facade.reports.medication.trend.report.ReportMedicationTrendFacade;
import com.rocs.medical.records.application.app.facade.reports.medication.trend.report.impl.ReportMedicationTrendFacadeImpl;
import com.rocs.medical.records.application.model.reports.MedicationTrendReport;

import com.rocs.medical.records.application.app.facade.reports.frequent.visit.report.FrequentVisitReportFacade;
import com.rocs.medical.records.application.app.facade.reports.frequent.visit.report.impl.FrequentVisitReportFacadeImpl;
import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

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
        System.out.println("3 - Retrieve Student Medical Record");
        System.out.println("4 - Frequent Visit Report");
        System.out.println("5 - Check Low Stock Medicine");

        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        switch (choice) {
            case 1: {
                CommonAilmentsReportFacade ailmentsReportFacade = new CommonAilmentsReportFacadeImpl();

                try {
                    scanner.nextLine();
                    System.out.println("Common Ailments Report");

                    Date startDate = getValidInputDate(scanner, dateFormat, "Enter start date (yyyy-MM-dd): ");
                    Date endDate = getValidInputDate(scanner, dateFormat, "Enter end date (yyyy-MM-dd): ");

                    System.out.print("Enter grade level (enter to skip): ");
                    String gradeLevel = scanner.nextLine().trim();
                    gradeLevel = gradeLevel.isEmpty() ? null : gradeLevel;

                    System.out.print("Enter section (enter to skip): ");
                    String section = scanner.nextLine().trim();
                    section = section.isEmpty() ? null : section;

                    List<CommonAilmentsReport> reports = ailmentsReportFacade.generateCommonAilmentReport(startDate, endDate, gradeLevel, section);
                    displayCommonAilmentsReport(reports, startDate, endDate, gradeLevel, section);
                } catch (RuntimeException e) {
                    System.out.println("Report generation failed: " + e.getMessage());
                }
                break;
            }

            case 2: {
                scanner.nextLine();
                ReportMedicationTrendFacade medicationTrendFacade = new ReportMedicationTrendFacadeImpl();

                try {
                    System.out.println("\nWelcome to Medication Trend Report");
                    SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy");
                    Date startDate = getValidInputDate(scanner, dateFormat, "Please enter start date (yyyy-MM-dd): ");
                    Date endDate = getValidInputDate(scanner, dateFormat, "Please enter end date (yyyy-MM-dd): ");

                    List<MedicationTrendReport> medicationTrendReportList = medicationTrendFacade.generateMedicationReport(startDate, endDate);
                    if (medicationTrendReportList == null || medicationTrendReportList.isEmpty()) {
                        System.out.println("No data available for the selected criteria.");
                        return;
                    }else{
                        System.out.println("\nMedication Trend report");
                        System.out.println("Period date: " + displayFormat.format(startDate) + " to " + displayFormat.format(endDate));
                        System.out.println("\nTotal no. of medicine usage within the period date: " + medicationTrendReportList.size());
                        for (MedicationTrendReport report : medicationTrendReportList) {
                            System.out.print("\nMedication Usage: " + report.getUsage());
                            System.out.print(" | Medicine: " + report.getMedicineName());
                            System.out.print(" | Medication Stocks: " + report.getStocks());
                        }
                    }
                } catch (RuntimeException e) {
                    System.out.println("Error generating: " + e.getMessage());
                }
                break;
            }
            case 3: {
                try {
                    scanner.nextLine();
                    StudentMedicalRecordFacadeImpl studentMedicalRecord = new StudentMedicalRecordFacadeImpl();
                    System.out.println("Search Student Medical Records using LRN: ");
                    long LRN = scanner.nextLong();
                    StudentMedicalRecord record = studentMedicalRecord.findMedicalInformationByLRN(LRN);
                    if (record == null) {
                        System.out.println(" Not Student Found");
                    } else {
                        System.out.println("Firstname             : " + record.getFirstName());
                        System.out.println("Middlename            : " + record.getMiddleName());
                        System.out.println("Lastname              : " + record.getLastName());
                        System.out.println("Age                   : " + record.getAge());
                        System.out.println("Gender                : " + record.getGender());
                        System.out.println("Symptoms              : " + record.getSymptoms());
                        System.out.println("Temperature Readings  : " + record.getTemperatureReadings());
                        System.out.println("Visit Date            : " + record.getVisitDate());
                        System.out.println("Treatment             : " + record.getTreatment());
                    }
                } catch (RuntimeException e) {
                    System.out.println("Error generating: " + e.getMessage());
                }
                break;
            }

            case 4: {
                scanner.nextLine();
                FrequentVisitReportFacade frequentVisitReportFacade = new FrequentVisitReportFacadeImpl();

                try {
                    System.out.println("Frequent Visit Report");
                    SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy");
                    Date frequentVisitStartDate = getValidInputDate(scanner, dateFormat, "Enter start date (yyyy-MM-dd): ");
                    Date frequentVisitEndDate = getValidInputDate(scanner, dateFormat, "Enter end date (yyyy-MM-dd): ");
                    System.out.print("Enter grade level for Frequent Visit: ");
                    String frequentVisitGradeLevel = scanner.nextLine().trim();

                    List<FrequentVisitReport> reports = frequentVisitReportFacade.generateFrequentVisitReport(frequentVisitStartDate, frequentVisitEndDate, frequentVisitGradeLevel);

                    if (reports == null || reports.isEmpty()) {
                        System.out.println("No data available for the selected criteria.");
                    }else{
                        System.out.println("Frequent Visit Report");
                        System.out.println("Period of Date: " + displayFormat.format(frequentVisitStartDate) + " to " + displayFormat.format(frequentVisitEndDate));
                        System.out.println("Total no. of Visit: " + reports.size());
                        for (FrequentVisitReport report : reports) {
                            System.out.println("\nStudent First Name: " + report.getFirstName());
                            System.out.println("\nStudent Last Name: " + report.getLastName());
                            System.out.println("\nVisit Date: " + report.getVisitDate());
                            System.out.println("\nGrade Level: " + report.getGradeLevel());
                            System.out.println("\nHealth Concern: " + report.getSymptoms());
                            System.out.println("\nTotal Visit: " + report.getVisitCount());
                        }
                    }


                } catch (RuntimeException e) {
                    System.out.println("Report generation failed: " + e.getMessage());
                }
                break;

            }
            case 5: {
                LowStockMedicineFacade lowStockMedicineFacade = new LowStockMedicineFacadeImpl();
                try {
                    List<LowStockItem> lowStockItems = lowStockMedicineFacade.findAllLowStockMedicine();
                    for (LowStockItem medicineInventory : lowStockItems) {
                        System.out.println("Medicine Name: " + medicineInventory.getDescription());
                        System.out.println("Current Stock Level: " + medicineInventory.getQuantityAvailable());
                        System.out.println("Notification: The stock level of " + medicineInventory.getDescription() + " is low. Current stock level: " + medicineInventory.getQuantityAvailable() + ". Please reorder supplies.");
                    }
                } catch (RuntimeException e) {
                    System.out.println("Error checking low stock items: " + e.getMessage());
                }
                break;
            }
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                break;
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
}