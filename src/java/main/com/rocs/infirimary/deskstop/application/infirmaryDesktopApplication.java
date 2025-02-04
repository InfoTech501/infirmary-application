package main.com.rocs.infirimary.deskstop.application;


import main.com.rocs.infirimary.deskstop.application.app.facade.PatientFacade;
import main.com.rocs.infirimary.deskstop.application.app.facade.patient.impl.PatientFacadeImpl;
import main.com.rocs.infirimary.deskstop.application.data.dbConnector.ConnectorHelper;
import main.com.rocs.infirimary.deskstop.application.model.patient.Patient;


import java.sql.Connection;
import java.util.Scanner;

public class infirmaryDesktopApplication {

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        PatientFacade PatientFacade = new PatientFacadeImpl();

        System.out.println(" Search for Students Medical Information / Record Using LRN : ");
        long LRN = sc.nextLong();


        Patient patient = PatientFacade.SearchMedInfoUsingLRN(LRN);


            if(patient == null ) {

                System.out.println("No Student Found");

            }else {

                System.out.println("Patient ID  :" + patient.getPatientId());
                System.out.println("Patient Firstname  : " + patient.getFirstName());
                System.out.println("Patient Middlename  :" + patient.getMiddleName());
                System.out.println("Patient Lastname : " + patient.getLastName());
                System.out.println("Symptoms : " + patient.getSymptoms());
                System.out.println("Added Remarks : " + patient.getAddedRemarks());
                System.out.println("Temperature Readings :" + patient.getTemperatureReadings());
                System.out.println("Visit Date : " + patient.getVisitDate());
                System.out.println("Time-In : " + patient.getTimeIn());
                System.out.println("Time-Out : " + patient.getTimeOut());
                System.out.println("Medication Administered  :" + patient.getMedicationAdministered());
                System.out.println("Nurse in Charge : " + patient.getNurseInCharge());


            }



    }
}



