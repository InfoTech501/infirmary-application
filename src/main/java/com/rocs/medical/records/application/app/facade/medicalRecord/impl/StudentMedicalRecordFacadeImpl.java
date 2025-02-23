package com.rocs.medical.records.application.app.facade.medicalRecord.impl;


import com.rocs.medical.records.application.app.facade.medicalRecord.StudentMedicalRecordFacade;
import com.rocs.medical.records.application.data.dao.medicalRecord.StudentMedicalRecordDao;
import com.rocs.medical.records.application.data.dao.medicalRecord.impl.StudentMedicalRecordDaoImpl;
import com.rocs.medical.records.application.model.medicalrecord.StudentMedicalRecord;

import java.util.ArrayList;
import java.util.List;

public class StudentMedicalRecordFacadeImpl implements StudentMedicalRecordFacade {

    private final StudentMedicalRecordDao StudentMedRecord = new StudentMedicalRecordDaoImpl();

    public void findMedicalInformationByLRN(long LRN) {

        StudentMedicalRecord record = StudentMedRecord.findMedicalInformationByLRN(LRN);


        if(record == null)
        {System.out.println(" Not Student Found");

        }else{

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


    }

    @Override
    public List<StudentMedicalRecord> findAllStudentMedicalRecords() {
        List<StudentMedicalRecord> medicalRecords = this.StudentMedRecord.getAllStudentMedicalRecords();

        for (StudentMedicalRecord record: medicalRecords){
            System.out.println();
            System.out.println("Firstname             : " + record.getFirstName());
            System.out.println("Middlename            : " + record.getMiddleName());
            System.out.println("Lastname              : " + record.getLastName());
            System.out.println("Age                   : " + record.getAge());
            System.out.println("Gender                : " + record.getGender());
            System.out.println("Symptoms              : " + record.getSymptoms());
            System.out.println("Temperature Readings  : " + record.getTemperatureReadings());
            System.out.println("Visit Date            : " + record.getVisitDate());
            System.out.println("Treatment             : " + record.getTreatment());

            System.out.println();
        }

        return medicalRecords;
    }
}




