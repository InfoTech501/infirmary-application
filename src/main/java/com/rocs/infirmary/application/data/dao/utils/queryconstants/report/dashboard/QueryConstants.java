package com.rocs.infirmary.application.data.dao.utils.queryconstants.report.dashboard;

public class QueryConstants {

    private final String GET_ALL_LOW_STOCK_MEDICINE_QUERY = "SELECT description, quantity_available FROM inventory WHERE quantity_available < 20";

    private final String GET_ALL_COMMON_AILMENTS_REPORT_QUERY = "SELECT a.description as AILMENT, COUNT(*) as occurrence_count, s.SECTION, s.GRADE_LEVEL, p.FIRST_NAME, p.LAST_NAME, p.AGE, s.STRAND " +
            "FROM MEDICAL_RECORD mr " +
            "JOIN AILMENTS a ON mr.AILMENT_ID = a.AILMENT_ID " +
            "JOIN STUDENT st ON mr.STUDENT_ID = st.ID " +
            "JOIN PERSON p ON st.PERSON_ID = p.ID " +
            "LEFT JOIN SECTION s ON st.SECTION_SECTION_ID = s.SECTION_ID " +
            "WHERE mr.VISIT_DATE BETWEEN ? AND ? " +
            "AND (UPPER(s.GRADE_LEVEL) = UPPER(?) OR ? IS NULL) " +
            "AND (UPPER(s.SECTION) = UPPER(?) OR ? IS NULL) " +
            "GROUP BY a.description, s.section, s.grade_level, p.FIRST_NAME, p.LAST_NAME, p.AGE, s.STRAND";

    private final String GET_FREQUENT_VISIT_REPORT_QUERY = "SELECT mr.student_id, p.first_name, p.last_name, s.grade_level, mr.visit_date, mr.symptoms, COUNT(*) AS visit_count\n" +
            "FROM medical_record mr\n" +
            "JOIN student st ON mr.student_id = st.id\n" +
            "JOIN section s ON st.section_section_id = s.section_id\n" +
            "JOIN person p ON st.person_id = p.id\n" +
            "WHERE s.grade_level = ? " +
            "AND mr.visit_date BETWEEN ? AND ?\n" +
            "GROUP BY mr.student_id, p.first_name, p.last_name, s.grade_level, mr.visit_date, mr.symptoms";

    private final  String GET_ALL_MEDICATION_TREND_REPORT = "SELECT i.medicine_id, COUNT (*) AS usage, m.item_name, i.quantity_available " +
            "FROM medicine_administered ma " +
            "JOIN medicine m ON m.medicine_id = ma.medicine_id " +
            "JOIN inventory i ON i.medicine_id = ma.medicine_id " +
            "JOIN medical_record mr ON mr.id = ma.med_record_id " +
            "WHERE mr.visit_date BETWEEN ? AND ? " +
            "GROUP BY i.medicine_id, m.item_name, i.quantity_available";

    public String getAllLowStockMedicineQuery() {
        return GET_ALL_LOW_STOCK_MEDICINE_QUERY;
    }

    public String getAllCommonAilmentReportQuery() {
        return GET_ALL_COMMON_AILMENTS_REPORT_QUERY;
    }

    public String getFrequentVisitReportsQuery() {
        return GET_FREQUENT_VISIT_REPORT_QUERY;
    }

    public String getAllMedicationTrendReport() {
        return GET_ALL_MEDICATION_TREND_REPORT;
    }
}
