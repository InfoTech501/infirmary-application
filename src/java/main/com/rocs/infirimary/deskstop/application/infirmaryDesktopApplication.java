package main.com.rocs.infirimary.deskstop.application;


import main.com.rocs.infirimary.deskstop.application.app.facade.StudentMedicalRecordFacade;
import main.com.rocs.infirimary.deskstop.application.app.facade.patient.impl.StudentMedicalRecordImplFacade;
import main.com.rocs.infirimary.deskstop.application.model.patient.StudentMedicalRecord;


import java.util.Scanner;

public class infirmaryDesktopApplication {

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        StudentMedicalRecordFacade StudentMedicalRecord = new StudentMedicalRecordImplFacade();

        System.out.println(" Search for Students Medical Information / Record Using LRN : ");
        long LRN = sc.nextLong();


        StudentMedicalRecord studentMedicalRecord = StudentMedicalRecord.SearchMedInfoUsingLRN(LRN);


            if(studentMedicalRecord == null ) {

                System.out.println("No Student Found");

            }else {

                System.out.println("Patient ID  :" + studentMedicalRecord.getPatientId());
                System.out.println("Patient Firstname  : " + studentMedicalRecord.getFirstName());
                System.out.println("Patient Middlename  :" + studentMedicalRecord.getMiddleName());
                System.out.println("Patient Lastname : " + studentMedicalRecord.getLastName());
                System.out.println("Symptoms : " + studentMedicalRecord.getSymptoms());
                System.out.println("Added Remarks : " + studentMedicalRecord.getAddedRemarks());
                System.out.println("Temperature Readings :" + studentMedicalRecord.getTemperatureReadings());
                System.out.println("Visit Date : " + studentMedicalRecord.getVisitDate());
                System.out.println("Time-In : " + studentMedicalRecord.getTimeIn());
                System.out.println("Time-Out : " + studentMedicalRecord.getTimeOut());
                System.out.println("Medication Administered  :" + studentMedicalRecord.getMedicationAdministered());
                System.out.println("Nurse in Charge : " + studentMedicalRecord.getNurseInCharge());


            }



    }
}



