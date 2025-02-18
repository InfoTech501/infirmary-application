package com.rocs.medical.records.application.app.facade.createMedicalRecords;

import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;
import java.time.LocalDateTime;

public interface MedicalRecordsFacade {
    boolean saveOrUpdateMedicalRecord(MedicalRecords record);

    MedicalRecords getMedicalRecordFromUserInput();
}