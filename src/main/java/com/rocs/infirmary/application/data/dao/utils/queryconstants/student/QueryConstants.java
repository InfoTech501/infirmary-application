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
     * query that delete a student's medical record as inactive by its student id.
     */
    public static String DELETE_STUDENT_MEDICAL_RECORD = "UPDATE MEDICAL_RECORD SET IS_ACTIVE = 0 WHERE STUDENT_ID = ?";
    /**
     * query that updates student's symptoms by LRN.
     */
    public static String UPDATE_STUDENT_SYMPTOMS = "UPDATE MEDICAL_RECORD mr SET mr.SYMPTOMS = ? WHERE mr.ID = (SELECT s.ID FROM STUDENT s WHERE s.LRN = ?)";
    /**
     * query that updates student's temperature readings by LRN.
     */
    public static String UPDATE_STUDENT_TEMPERATURE_READINGS = "UPDATE MEDICAL_RECORD mr SET mr.TEMPERATURE_READINGS = ? WHERE mr.ID = (SELECT s.ID FROM STUDENT s WHERE s.LRN = ?)";
    /**
     * query that updates student's visit date by LRN.
     */
    public static String UPDATE_STUDENT_VISIT_DATE = "UPDATE MEDICAL_RECORD mr SET mr.VISIT_DATE = ? WHERE mr.ID = (SELECT s.ID FROM STUDENT s WHERE s.LRN = ?)";
    /**
     * query that updates student's treatment info by LRN.
     */
    public static String UPDATE_STUDENT_TREATMENT = "UPDATE MEDICAL_RECORD mr SET mr.TREATMENT = ? WHERE mr.ID = (SELECT s.ID FROM STUDENT s WHERE s.LRN = ?)";

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

    public String selectStudentHealthProfile() {
        return SELECT_STUDENT_HEALTH_PROFILE_QUERY;
    }

    public String selectStudentHealthProfileByLrn() {
        return SELECT_STUDENT_HEALTH_PROFILE_BY_LRN;
    }

}
