package com.rocs.medical.records.application.app.facade.medicneInfirmary;

public interface MedicineFacade {

    /**
     * Deletes a medicine record from the inventory based on the provided medicine ID.
     * @param medicineId The unique identifier of the medicine to be deleted.
     * @return true if the deletion was successful, false if the medicine ID was not found.
     */
    boolean deleteMedicineById(String medicineId);
}
