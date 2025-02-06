package com.rocs.frequent.visit.function.model;

import com.rocs.frequent.visit.function.FrequentVisitSv;
import com.rocs.frequent.visit.function.model.report.Patients;

import java.util.List;
import java.util.Scanner;

//import all the classes and interfaces into the main

public class FrequentVisitApp {
    public static void main(String[] args) {
        FrequentVisitSv frequentVisitService = new FrequentVisitSv();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter grade level: ");
        String gradeLevel = scanner.nextLine();

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        List<Patients> report = frequentVisitService.generateReport(gradeLevel, startDate, endDate);
        frequentVisitService.displayReport(report);

        scanner.close();
    }
}
