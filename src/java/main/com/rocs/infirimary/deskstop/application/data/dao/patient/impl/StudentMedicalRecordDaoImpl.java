
package main.com.rocs.infirimary.deskstop.application.data.dao.patient.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.com.rocs.infirimary.deskstop.application.data.dao.patient.StudentMedicalRecordDao;
import main.com.rocs.infirimary.deskstop.application.data.dbConnector.ConnectorHelper;
import main.com.rocs.infirimary.deskstop.application.model.patient.StudentMedicalRecord;


public class StudentMedicalRecordDaoImpl implements StudentMedicalRecordDao {


    public StudentMedicalRecord SearchMedInfoUsingLRN(long LRN) {

       StudentMedicalRecord studentMedicalRecord = null;


        try {
            try (Connection con = ConnectorHelper.getConnection()) {

                PreparedStatement stmt = con.prepareStatement("SELECT " +
                        "p.patient_id, p.first_name AS patient_first_name, p.middle_name AS patient_middle_name, " +
                        "p.last_name AS patient_last_name, p.symptoms, p.added_remarks, p.temperature_readings, " +
                        "p.visit_date, p.time_in, p.time_out, p.medications_administered, p.nurse_in_charge " +
                        "FROM patient p " +
                        "LEFT JOIN student s ON p.student_number = s.student_number " +
                        "WHERE s.lrn = ?");


                stmt.setLong(1, LRN);
                ResultSet rs = stmt.executeQuery();


                while (rs.next()) {

                    studentMedicalRecord = new StudentMedicalRecord();

                    studentMedicalRecord.setPatientId(rs.getInt("PATIENT_ID"));
                    studentMedicalRecord.setFirstName(rs.getString("PATIENT_FIRST_NAME"));
                    studentMedicalRecord.setMiddleName(rs.getString("PATIENT_MIDDLE_NAME"));
                    studentMedicalRecord.setLastName(rs.getString("PATIENT_LAST_NAME"));
                    studentMedicalRecord.setSymptoms(rs.getString("SYMPTOMS"));
                    studentMedicalRecord.setAddedRemarks(rs.getString("ADDED_REMARKS"));
                    studentMedicalRecord.setTemperatureReadings(rs.getString("TEMPERATURE_READINGS"));
                    studentMedicalRecord.setVisitDate(rs.getString("VISIT_DATE"));
                    studentMedicalRecord.setTimeIn(rs.getString("TIME_IN"));
                    studentMedicalRecord.setTimeOut(rs.getString("TIME_OUT"));
                    studentMedicalRecord.setMedicationAdministered("MEDICATION_ADMINISTERED");
                    studentMedicalRecord.setNurseInCharge(rs.getString("NURSE_IN_CHARGE"));


                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return studentMedicalRecord;
    }
}



