package com.rocs.student.medical.records.application;

import com.rocs.student.medical.records.application.app.facade.studentmedicalreport.StudentMedicalReportFacade;
import com.rocs.student.medical.records.application.app.facade.studentmedicalreport.impl.StudentMedicalReportFacadeImpl;
import com.rocs.student.medical.records.application.model.studentmedicalreport.StudentMedicalReport;

import java.util.Scanner;

public class InfirmarySystemApplication {

    public static void main(String[] args) {
        StudentMedicalReportFacade studentFacade = new StudentMedicalReportFacadeImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Student Medical Records System");
        System.out.print("Enter Student ID: ");
        int patientId = scanner.nextInt();


        StudentMedicalReport student = studentFacade.getStudentDetails(patientId);

        if (student != null) {
            System.out.println("Full Name: " + student.getFullName());
            System.out.println("Symptoms: " + student.symptoms);
            System.out.println("Remarks: " + student.addedRemarks);
            System.out.println("Temperature: " + student.temperatureReadings);
            System.out.println("Visit In: " + student.visitDateTimeIn);
            System.out.println("Visit Out: " + student.visitDateTimeOut);
            System.out.println("Medications: " + student.medicationsAdministered);
        } else {
            System.out.println("No record found for Student ID: " + patientId);
        }

        scanner.close();
    }
}


