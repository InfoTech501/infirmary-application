package com.rocs.infirmary.application.data.dao.medicine.inventory;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;

import java.util.List;
public interface MedicineInventoryDao {


    List<Medicine> getAllMedicine();
}
