package com.rocs.infirmary.application.data.dao.utils.queryconstants.record;

/**
 * the {@code QueryConstants} class handles the static method for database queries.
 */
public class QueryConstants {

    /**
     * query that retrieves all student medical record.
     */
    public static String GET_ALL_STUDENTS_MEDICAL_RECORDS = "SELECT " +
            "    student.id, " +
            "    student.person_id, " +
            "    student.lrn, " +
            "    person.first_name, " +
            "    person.middle_name, " +
            "    person.last_name, " +
            "    section.grade_level, " +
            "    section.section, " +
            "    person.age, " +
            "    person.gender, " +
            "    person.email, " +
            "    person.address, " +
            "    person.contact_number, " +
            "    medical_record.symptoms, " +
            "    medical_record.temperature_readings, " +
            "    medical_record.blood_pressure, " +
            "    medical_record.pulse_rate, " +
            "    medical_record.respiratory_rate, " +
            "    medical_record.visit_date, " +
            "    medical_record.treatment, " +
            "    medicine.item_name AS medicine_name, " +
            "    medicine_administered.quantity AS medicine_quantity, " +
            "    nurse_person.first_name AS nurse_first_name, " +
            "    nurse_person.last_name AS nurse_last_name " +
            "FROM medical_record " +
            "JOIN student ON medical_record.student_id = student.id " +
            "JOIN person ON student.person_id = person.id " +
            "LEFT JOIN section ON student.section_section_id = section.section_id " +
            "LEFT JOIN medicine_administered ON medical_record.id = medicine_administered.med_record_id " +
            "LEFT JOIN medicine ON medicine_administered.medicine_id = medicine.medicine_id " +
            "LEFT JOIN employee ON medical_record.nurse_in_charge_id = employee.id " +
            "LEFT JOIN person nurse_person ON employee.id = nurse_person.id " +
            "WHERE MEDICAL_RECORD.IS_ACTIVE = 1";

    public static  String GET_LAST_INSERTED_MEDICAL_RECORD_ID = "SELECT id FROM medical_record WHERE student_id = ? ORDER BY id DESC FETCH FIRST 1 ROWS ONLY";
    /**
     * query that add student medical record.
     */
    public static String ADD_STUDENT_MEDICAL_RECORD = "INSERT INTO MEDICAL_RECORD (STUDENT_ID, AILMENT_ID, NURSE_IN_CHARGE_ID, SYMPTOMS, TEMPERATURE_READINGS, BLOOD_PRESSURE, PULSE_RATE, RESPIRATORY_RATE, VISIT_DATE, TREATMENT, IS_ACTIVE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    /**
     * query that add a record into medicine administered.
     */
    public static String ADD_MEDICINE_ADMINISTERED = "INSERT INTO MEDICINE_ADMINISTERED (MEDICINE_ID, MED_RECORD_ID, NURSE_IN_CHARGE_ID, DESCRIPTION, QUANTITY, DATE_ADMINISTERED) VALUES (?,?, ?, ?, ?, ?)";
    /**
     * query that add a new ailment.
     */
    public static String ADD_NEW_AILMENTS = "INSERT INTO AILMENTS (DESCRIPTION) VALUES (?)";
    /**
     * query that retrieves the ailment_id by matching symptoms with the description.
     */
    public static String FIND_AILMENT_ID_BY_SYMPTOMS = "SELECT AILMENT_ID FROM AILMENTS WHERE LOWER(DESCRIPTION) LIKE ?";
}
