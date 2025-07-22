package com.rocs.infirmary.application.data.dao.utils.queryconstants.student;

public class QueryConstants {

    public static final String GET_ALL_ACTIVE_MEDICAL_INFORMATION_BY_LRN = "SELECT " +
            "mr.id AS medical_record_id, " +
            "s.id AS student_id, " +
            "s.LRN, " +
            "p.first_name, " +
            "p.middle_name, " +
            "p.last_name, " +
            "p.age, " +
            "p.gender, " +
            "mr.symptoms, " +
            "mr.pulse_rate, " +
            "mr.respiratory_rate," +
            "mr.blood_pressure," +
            "mr.temperature_readings, " +
            "mr.visit_date AS visit_date, " +
            "mr.treatment, " +
            "mr.is_active " +
            "FROM student s " +
            "JOIN person p ON s.person_id = p.id " +
            "INNER JOIN medical_record mr ON s.id = mr.student_id " +
            "WHERE mr.is_active = 1 AND s.LRN = ?";

    public static final String GET_MEDICAL_INFORMATION_BY_ID = "SELECT " +
            "mr.id AS medical_record_id, " +
            "s.id AS student_id, " +
            "s.LRN, " +
            "p.first_name, " +
            "p.middle_name, " +
            "p.last_name, " +
            "p.age, " +
            "p.gender, " +
            "mr.symptoms, " +
            "mr.pulse_rate, " +
            "mr.respiratory_rate," +
            "mr.blood_pressure," +
            "mr.temperature_readings, " +
            "mr.visit_date AS visit_date, " +
            "mr.treatment, " +
            "mr.is_active " +
            "FROM student s " +
            "JOIN person p ON s.person_id = p.id " +
            "INNER JOIN medical_record mr ON s.id = mr.student_id " +
            "WHERE mr.is_active = 1 AND mr.id = ?";

    public static String GET_ALL_STUDENTS_MEDICAL_RECORDS = "SELECT " +
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
            "medical_record.is_active " +
            "FROM medical_record " +
            "JOIN person ON medical_record.student_id = person.id " +
            "LEFT JOIN student ON medical_record.student_id = student.id";


    public static final String DELETE_STUDENT_MEDICAL_RECORD = "UPDATE MEDICAL_RECORD SET IS_ACTIVE = 0 WHERE ID = ?";

    public static final String UPDATE_STUDENT_SYMPTOMS = "UPDATE MEDICAL_RECORD mr SET mr.SYMPTOMS = ? WHERE mr.ID = ?";

    public static final String UPDATE_STUDENT_TEMPERATURE_READINGS = "UPDATE MEDICAL_RECORD mr SET mr.TEMPERATURE_READINGS = ? WHERE mr.ID = ?";

    public static final String UPDATE_STUDENT_VISIT_DATE = "UPDATE MEDICAL_RECORD mr SET mr.VISIT_DATE = ? WHERE mr.ID = ?";

    public static final String UPDATE_STUDENT_TREATMENT = "UPDATE MEDICAL_RECORD mr SET mr.TREATMENT = ? WHERE mr.ID = ?";

    public static final String SELECT_STUDENT_HEALTH_PROFILE_QUERY = "SELECT * FROM (" +
            "  SELECT " +
            "    mr.is_active, p.first_name, p.middle_name, p.last_name, p.gender, p.age, p.contact_number, p.birthdate, p.address, " +
            "    section.section, student.lrn, section.grade_level, adviser.first_name AS adviser_first_name, " +
            "    mr.visit_date, nurse.first_name AS NURSE_IN_CHARGE, nurse.last_name AS NURSE_LAST_NAME, " +
            "    ROW_NUMBER() OVER (PARTITION BY student.id ORDER BY mr.visit_date DESC) rn " +
            "  FROM MEDICAL_RECORD mr " +
            "  JOIN PERSON p ON mr.STUDENT_ID = p.ID " +
            "  JOIN STUDENT ON mr.STUDENT_ID = student.ID " +
            "  JOIN SECTION ON student.SECTION_SECTION_ID = section.SECTION_ID " +
            "  JOIN PERSON nurse ON mr.nurse_in_charge_id = nurse.id " +
            "  LEFT JOIN PERSON adviser ON section.ADVISER_ID = adviser.ID " +
            "  WHERE mr.is_active = 1" +
            ") WHERE rn = 1";

    public static final String SELECT_STUDENT_HEALTH_PROFILE_BY_LRN = "SELECT mr.is_active, p.first_name, p.middle_name,p.last_name,p.contact_number,p.email,p.address,mr.symptoms,mr.temperature_readings,visit_date,nurse.first_name as NURSE_FIRST_NAME, nurse.last_name as NURSE_LAST_NAME,mr.treatment\n" +
            "FROM MEDICAL_RECORD mr\n" +
            "JOIN PERSON p ON mr.STUDENT_ID = p.ID\n" +
            "JOIN STUDENT ON mr.STUDENT_ID = student.ID\n" +
            "JOIN SECTION ON student.SECTION_SECTION_ID = section.SECTION_ID\n" +
            "JOIN Person nurse ON mr.nurse_in_charge_id = nurse.id\n" +
            "LEFT JOIN PERSON adviser ON section.ADVISER_ID = adviser.ID\n" +
            "WHERE LRN = ?";
}
