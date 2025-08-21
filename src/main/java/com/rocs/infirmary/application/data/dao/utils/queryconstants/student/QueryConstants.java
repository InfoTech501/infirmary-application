package com.rocs.infirmary.application.data.dao.utils.queryconstants.student;

/**
 * the {@code QueryConstants} class handles the static method for database queries.
 */
public class QueryConstants {
    /**
     * query that retrieves all medical information by its LRN.
     */
    public static String GET_ALL_MEDICAL_INFORMATION_BY_LRN = "SELECT\n" +
            "  s.id AS student_id,\n" +
            "  s.LRN,\n" +
            "  p.first_name,\n" +
            "  p.middle_name,\n" +
            "  p.last_name,\n" +
            "  p.age,\n" +
            "  p.gender,\n" +
            "  sec.grade_level,\n" +
            "  sec.section,\n" +
            "  mr.symptoms,\n" +
            "  mr.temperature_readings,\n" +
            "  mr.visit_date AS visit_date,\n" +
            "  mr.treatment,\n" +
            "  m.item_name AS medicine_name,\n" +
            "  ma.quantity AS dispensing_out\n" +
            "FROM student s\n" +
            "JOIN person p ON s.person_id = p.id\n" +
            "LEFT JOIN section sec ON sec.section_id = sec.section_id\n" +
            "LEFT JOIN medical_record mr ON s.id = mr.student_id\n" +
            "LEFT JOIN medicine_administered ma ON mr.id = ma.med_record_id\n" +
            "LEFT JOIN medicine m ON ma.medicine_id = m.medicine_id\n" +
            "WHERE s.LRN = ?";
    /**
     * query that delete a student's medical record as inactive using its unique medical record id.
     */
    public static String DELETE_STUDENT_MEDICAL_RECORD = """ 
            UPDATE MEDICAL_RECORD
            SET IS_ACTIVE = 0
            WHERE ID = ?""";
    /**
     * query that updates student's symptoms by medical record id.
     */
    public static String UPDATE_STUDENT_SYMPTOMS = "UPDATE MEDICAL_RECORD SET SYMPTOMS = ? WHERE ID = ?";
    /**
     * query that updates student's temperature readings by medical record id.
     */
    public static String UPDATE_STUDENT_TEMPERATURE_READINGS = "UPDATE MEDICAL_RECORD SET TEMPERATURE_READINGS = ? WHERE ID = ?";
    /**
     * query that updates student's visit date by medical record id.
     */
    public static String UPDATE_STUDENT_VISIT_DATE = "UPDATE MEDICAL_RECORD SET VISIT_DATE = ? WHERE ID = ?";
    /**
     * query that updates student's treatment info by medical record id.
     */
    public static String UPDATE_STUDENT_TREATMENT = "UPDATE MEDICAL_RECORD SET TREATMENT = ? WHERE ID = ?";

    /**
     * query that retrieves all student health profile with their most recent medical record.
     * the students without medical records will still appear.
     */
    public static final String SELECT_STUDENT_HEALTH_PROFILE_QUERY = """
            SELECT * FROM (
              SELECT
                    COALESCE(mr.is_active, 0) as is_active,
                    p.first_name, p.middle_name, p.last_name, p.gender, p.age, p.contact_number, p.birthdate, p.address,
                    section.section, student.lrn, section.grade_level,
                    adviser.first_name AS adviser_first_name,
                    adviser.last_name AS adviser_last_name,
                    mr.visit_date,
                    nurse.first_name AS NURSE_IN_CHARGE,
                    nurse.last_name AS NURSE_LAST_NAME,
                    ROW_NUMBER() OVER (PARTITION BY student.id ORDER BY COALESCE(mr.visit_date, TO_DATE('1900-01-01', 'YYYY-MM-DD')) DESC) rn
              FROM STUDENT
              JOIN PERSON p ON student.PERSON_ID = p.ID
              JOIN SECTION ON student.SECTION_SECTION_ID = section.SECTION_ID
              LEFT JOIN PERSON adviser ON section.ADVISER_ID = adviser.ID
              LEFT JOIN MEDICAL_RECORD mr ON student.ID = mr.STUDENT_ID AND mr.is_active = 1
              LEFT JOIN PERSON nurse ON mr.nurse_in_charge_id = nurse.id
            ) WHERE rn = 1""";

    /**
     * query that retrieves all active student health profile using lrn
     * */
    public static final String SELECT_STUDENT_HEALTH_PROFILE_BY_LRN = """
            SELECT
            mr.is_active, mr.id, p.first_name, p.middle_name, p.last_name,
            p.contact_number,p.email,p.address, mr.symptoms, mr.temperature_readings, visit_date,
            nurse.first_name as NURSE_FIRST_NAME, nurse.last_name as NURSE_LAST_NAME,
            mr.treatment,
            mr.blood_pressure,
            mr.respiratory_rate,
            mr.pulse_rate,
            mr.temperature_readings,
            adviser.first_name AS adviser_first_name, adviser.last_name AS adviser_last_name
            FROM MEDICAL_RECORD mr
            JOIN PERSON p ON mr.STUDENT_ID = p.ID
            JOIN STUDENT ON mr.STUDENT_ID = student.ID
            JOIN SECTION ON student.SECTION_SECTION_ID = section.SECTION_ID
            JOIN Person nurse ON mr.nurse_in_charge_id = nurse.id
            LEFT JOIN PERSON adviser ON section.ADVISER_ID = adviser.ID
            WHERE LRN = ? AND mr.IS_ACTIVE = 1""";

}
