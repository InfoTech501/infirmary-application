package com.rocs.infirmary.application.data.dao.utils.queryconstants.student;

/**
 * the {@code QueryConstants} class handles the static method for database queries.
 */
public class QueryConstants {
    /**
     * query that retrieves all medical information by its LRN.
     */
    public static String GET_ALL_MEDICAL_INFORMATION_BY_LRN = "SELECT " +
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
    /**
     * query that retrieves all student medical record.
     */
    public static String GET_ALL_STUDENTS_MEDICAL_RECORDS = "SELECT\n" +
            "    student.id,\n" +
            "    student.person_id,\n" +
            "    student.lrn,\n" +
            "    person.first_name,\n" +
            "    person.middle_name,\n" +
            "    person.last_name,\n" +
            "    section.grade_level,\n" +
            "    section.section,\n" +
            "    person.age,\n" +
            "    person.gender,\n" +
            "    person.email,\n" +
            "    person.address,\n" +
            "    person.contact_number,\n" +
            "    medical_record.symptoms,\n" +
            "    medical_record.temperature_readings,\n" +
            "    medical_record.blood_pressure,\n" +
            "    medical_record.pulse_rate,\n" +
            "    medical_record.respiratory_rate,\n" +
            "    medical_record.visit_date,\n" +
            "    medical_record.treatment,\n" +
            "    medicine.item_name AS medicine_name,\n" +
            "    medicine_administered.quantity AS medicine_quantity,\n" +
            "    nurse_person.first_name AS nurse_first_name,\n" +
            "    nurse_person.last_name AS nurse_last_name\n" +
            "FROM medical_record\n" +
            "JOIN student ON medical_record.student_id = student.id\n" +
            "JOIN person ON student.person_id = person.id\n" +
            "LEFT JOIN section ON student.section_section_id = section.section_id\n" +
            "LEFT JOIN medicine_administered ON medical_record.id = medicine_administered.med_record_id\n" +
            "LEFT JOIN medicine ON medicine_administered.medicine_id = medicine.medicine_id\n" +
            "LEFT JOIN employee ON medical_record.nurse_in_charge_id = employee.id\n" +
            "LEFT JOIN person nurse_person ON employee.id = nurse_person.id ";
    /**
     * query that add student medical record.
     */
    public static String ADD_STUDENT_MEDICAL_RECORD = "INSERT INTO MEDICAL_RECORD (STUDENT_ID, AILMENT_ID, NURSE_IN_CHARGE_ID, SYMPTOMS, TEMPERATURE_READINGS, BLOOD_PRESSURE, PULSE_RATE, RESPIRATORY_RATE, VISIT_DATE, TREATMENT, IS_ACTIVE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    /**
     * query that add a record into medicine administered.
     */
    public static String ADD_MEDICINE_ADMINISTERED = "INSERT INTO MEDICINE_ADMINISTERED (MEDICINE_ID, NURSE_IN_CHARGE_ID, DESCRIPTION, QUANTITY, DATE_ADMINISTERED) VALUES (?, ?, ?, ?, ?)";
    /**
     * query that retrieves basic nurse identity info from person and employee tables.
     */
    public static String GET_ALL_NURSE_EMPLOYEE = "SELECT p.id, p.first_name, p.middle_name, p.last_name " +
            "FROM person p " +
            "JOIN employee e ON p.id = e.id ";
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
    /**
     * query that retrieves the ailment_id by matching symptoms with the description.
     */
    public static String FIND_AILMENT_ID_BY_SYMPTOMS = "SELECT ailment_id FROM ailments WHERE LOWER(description) LIKE ?";

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
