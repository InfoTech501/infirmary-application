package main.com.rocs.infirimary.deskstop.application;


import main.com.rocs.infirimary.deskstop.application.app.facade.StudentMedicalRecordFacade;
import main.com.rocs.infirimary.deskstop.application.app.facade.medicalRecord.impl.StudentMedicalRecordImplFacade;
import main.com.rocs.infirimary.deskstop.application.model.medicalrecord.StudentMedicalRecord;
import main.com.rocs.infirimary.deskstop.application.model.studentdetails.StudentDetails;


import java.util.Scanner;

public class infirmaryDesktopApplication {

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        StudentMedicalRecordFacade StudentMedicalRecord = new StudentMedicalRecordImplFacade();

        System.out.println(" Search Student Medical Records using LRN: ");
        long LRN = sc.nextLong();


        StudentMedicalRecord record = StudentMedicalRecord.SearchMedInfoUsingLRN(LRN);

        StudentDetails details = record.getStudentDetails();

        if( details == null ) {

            System.out.println("No Student Found");
        }else {

            System.out.println("Firstname             : " + details.getFirstName());
            System.out.println("Middlename            : " + details.getMiddleName());
            System.out.println("Lastname              : " + details.getLastName());
            System.out.println("Age                   : " + details.getAge());
            System.out.println("Gender                : " + details.getGender());
            System.out.println("Email                 : " + details.getEmail());
            System.out.println("Address               : " + details.getAddress());
            System.out.println("Contact Number        : " + details.getContactNumber());

            System.out.println("Ailment ID            : "  + record.getAilmentID());
            System.out.println("Medical History ID    : "  + record.getMedHistoryID());
            System.out.println("Nurse In Charge ID    : "  + record.getNurseInChargeID());
            System.out.println("Symptoms              : "  + record.getSymptoms());
            System.out.println("Temperature Readings  : "  + record.getTemperatureReadings());
            System.out.println("Visit Date            : "  + record.getVisitDate());
            System.out.println("Treatment             : "  + record.getTreatment());

        }



    }
}



