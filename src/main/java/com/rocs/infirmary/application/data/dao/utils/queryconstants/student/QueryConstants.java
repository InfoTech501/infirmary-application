package com.rocs.infirmary.application.data.dao.utils.queryconstants.student;

public class QueryConstants {

    private final String GET_ALL_MEDICAL_INFORMATION_BY_LRN = "SELECT " +
            "s.id AS student_id, " +
            "s.LRN, " +
            "p.first_name, " +
            "p.middle_name, " +
            "p.last_name, " +
            "p.age, " +
            "p.gender, " +
            "mr.symptoms, " +
            "mr.temperature_readings, " +
            "mr.visit_date AS visit_date, " +
            "mr.treatment " +
            "FROM student s " +
            "JOIN person p ON s.person_id = p.id " +
            "LEFT JOIN medical_record mr ON s.id = mr.student_id " +
            "WHERE s.LRN = ?";

    private final String GET_ALL_STUDENTS_MEDICAL_RECORDS = "SELECT " +
            "student.id, " +
            "person.first_name, " +
            "person.middle_name, " +
            "person.last_name, " +
            "person.age, " +
            "person.gender, " +
            "medical_record.symptoms, " +
            "medical_record.temperature_readings, " +
            "medical_record.visit_date, " +
            "medical_record.treatment " +
            "FROM medical_record " +
            "JOIN person ON medical_record.student_id = person.id " +
            "LEFT JOIN student ON medical_record.student_id = student.id";


    private final String DELETE_STUDENT_MEDICAL_RECORD = "UPDATE MEDICAL_RECORD SET IS_ACTIVE = 0 WHERE STUDENT_ID = ?";

    private final String UPDATE_STUDENT_SYMPTOMS = "UPDATE MEDICAL_RECORD mr SET mr.SYMPTOMS = ? WHERE mr.ID = (SELECT s.ID FROM STUDENT s WHERE s.LRN = ?)";

    private final String UPDATE_STUDENT_TEMPERATURE_READINGS = "UPDATE MEDICAL_RECORD mr SET mr.TEMPERATURE_READINGS = ? WHERE mr.ID = (SELECT s.ID FROM STUDENT s WHERE s.LRN = ?)";

    private final String UPDATE_STUDENT_VISIT_DATE = "UPDATE MEDICAL_RECORD mr SET mr.VISIT_DATE = ? WHERE mr.ID = (SELECT s.ID FROM STUDENT s WHERE s.LRN = ?)";

    private final String UPDATE_STUDENT_TREATMENT = "UPDATE MEDICAL_RECORD mr SET mr.TREATMENT = ? WHERE mr.ID = (SELECT s.ID FROM STUDENT s WHERE s.LRN = ?)";

    private final String SELECT_STUDENT_HEALTH_PROFILE_QUERY = "SELECT p.first_name, p.middle_name,p.last_name,section.section,student.lrn,section.grade_level,adviser.first_name AS adviser_first_name,mr.symptoms,mr.temperature_readings,visit_date,nurse.first_name as NURSE_IN_CHARGE\n" +
            "FROM MEDICAL_RECORD mr\n" +
            "JOIN PERSON p ON mr.STUDENT_ID = p.ID\n" +
            "JOIN STUDENT ON mr.STUDENT_ID = student.ID\n" +
            "JOIN SECTION ON student.SECTION_SECTION_ID = section.SECTION_ID\n" +
            "JOIN Person nurse ON mr.nurse_in_charge_id = nurse.id\n" +
            "LEFT JOIN PERSON adviser ON section.ADVISER_ID = adviser.ID";

    private final String SELECT_STUDENT_HEALTH_PROFILE_BY_LRN = "SELECT p.first_name, p.middle_name,p.last_name,p.contact_number,p.email,p.address,mr.symptoms,mr.temperature_readings,visit_date,nurse.first_name as NURSE_IN_CHARGE,mr.treatment\n" +
            "FROM MEDICAL_RECORD mr\n" +
            "JOIN PERSON p ON mr.STUDENT_ID = p.ID\n" +
            "JOIN STUDENT ON mr.STUDENT_ID = student.ID\n" +
            "JOIN SECTION ON student.SECTION_SECTION_ID = section.SECTION_ID\n" +
            "JOIN Person nurse ON mr.nurse_in_charge_id = nurse.id\n" +
            "LEFT JOIN PERSON adviser ON section.ADVISER_ID = adviser.ID\n" +
            "WHERE LRN = ?";


    public String getAllStudentMedicalRecords() { return GET_ALL_STUDENTS_MEDICAL_RECORDS;
    }
    public String getAllMedicalInformationByLRN() { return GET_ALL_MEDICAL_INFORMATION_BY_LRN;
    }
    public String deleteStudentMedicalRecord() { return DELETE_STUDENT_MEDICAL_RECORD;
    }

    public String updateStudentSymptoms () {return UPDATE_STUDENT_SYMPTOMS; };

    public String updateStudentTemperatureReadings () {return UPDATE_STUDENT_TEMPERATURE_READINGS; }

    public String updateStudentVisitDate () {return UPDATE_STUDENT_VISIT_DATE; }

    public String updateStudentTreatment () {return UPDATE_STUDENT_TREATMENT; }

    public String selectStudentHealthProfile() {
        return SELECT_STUDENT_HEALTH_PROFILE_QUERY;
    }

    public String selectStudentHealthProfileByLrn() {
        return SELECT_STUDENT_HEALTH_PROFILE_BY_LRN;
    }

}
