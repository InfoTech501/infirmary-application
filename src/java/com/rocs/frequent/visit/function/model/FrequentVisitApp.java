package com.rocs.frequent.visit.function.model;

import com.rocs.frequent.visit.function.controller.FrequentVisitController;
import com.rocs.frequent.visit.function.model.report.Patients;

import java.util.List;
import java.util.Scanner;

public class FrequentVisitApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FrequentVisitController controller = new FrequentVisitController();

        System.out.print("Enter grade level: ");
        String gradeLevel = scanner.nextLine();

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        List<Patients> report = controller.getFrequentVisitorsReport(gradeLevel, startDate, endDate);

        if (report.isEmpty()) {
            System.out.println("No data is available for the selected period.");
        } else {
            System.out.println("Frequent Clinic Visits Report:");

            for (Patients patient : report) {
                System.out.println("ID: " + patient.getId() + ", Name: " + patient.getLast_name() + ", Grade Level: " + patient.getGradeLevel() + ", Total Visit: " + patient.getVisitCount());
            }
        }

        scanner.close();
    }
}