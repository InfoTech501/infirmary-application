package com.rocs.medical.records.application.app.facade.createMedicalRecords;

import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;

/**
 * An interface that manages the report of medication trend within the given report period
 * * @param startDate  The start date of the report period.
 * * @param endDate    The end date of the report period.
 * * @return list of MedicationTrendReports such as usage, medicine name and stocks.
 * */
public interface MedicalRecordsFacade {
    boolean saveOrcancelMedicalRecord(MedicalRecords record);

    MedicalRecords getMedicalRecordFromUserInput();
}