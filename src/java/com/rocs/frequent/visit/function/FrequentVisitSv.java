package com.rocs.frequent.visit.function;

import com.rocs.frequent.visit.function.data.facade.impl.FrequentVisitFacadeImpl;
import com.rocs.frequent.visit.function.model.report.Patients;
import com.rocs.frequent.visit.function.data.facade.FrequentVisitFacade;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FrequentVisitSv {
    private FrequentVisitFacade freqfacade;

    public FrequentVisitSv() {
        this.freqfacade = new FrequentVisitFacadeImpl();
    }

    public List<Patients> generateReport(String gradeLevel, String startDate, String endDate) {
        return freqfacade.generateFrequentVisitReport(gradeLevel, startDate, endDate);
    }

    public void displayReport(List<Patients> report) {
        if (report.isEmpty()) {
            System.out.println("No data available for the selected period.");
        } else {
            // Display the Students visit details
            for (Patients patient : report) {
                System.out.println("ID: " + patient.getId());
                System.out.println("Student Number: " + patient.getStudent_number());
                System.out.println("Name: " + patient.getLast_name());
                System.out.println("Grade Level: " + patient.getGradeLevel());
                System.out.println("Symptoms: " + patient.getSymptoms());
                System.out.println("Visit Date: " + patient.getVisitDate());
                System.out.println();
            }

            // This code calculates the monthly summary of each student
            Map<String, Long> visitCounts = report.stream()
                    .collect(Collectors.groupingBy(Patients::getStudent_number, Collectors.counting()));

            System.out.println("Monthly Summary:");
            for (Map.Entry<String, Long> entry : visitCounts.entrySet()) {
                String studentNumber = entry.getKey();
                long totalVisits = entry.getValue();
                Optional<Patients> optionalPatient = report.stream().filter(p -> p.getStudent_number().equals(studentNumber)).findFirst();
                if (optionalPatient.isPresent()) {
                    Patients patient = optionalPatient.get();
                    System.out.println("ID: " + patient.getId());
                    System.out.println("Student Number: " + patient.getStudent_number());
                    System.out.println("Last Name: " + patient.getLast_name());
                    System.out.println("Total Visits: " + totalVisits);
                    System.out.println("Symptoms: " + patient.getStudent_number());
                    System.out.println();
                }
            }
        }
    }
}