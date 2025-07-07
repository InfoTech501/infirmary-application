package com.rocs.infirmary.application.data.model.inventory.medicine;

import com.rocs.infirmary.application.data.model.inventory.Inventory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.sql.Timestamp;

public class Medicine extends Inventory{

    private Long medicineId;

    private String itemName;

    private String description;

    private Timestamp expirationDate;

    private BooleanProperty isSelected = new SimpleBooleanProperty();
    private boolean hasSelect;


    public Medicine() {

    }

    public Medicine(Long medicineId, String itemName, String description, Timestamp expirationDate, BooleanProperty isSelected) {
        this.medicineId = medicineId;
        this.itemName = itemName;
        this.description = description;
        this.expirationDate = expirationDate;
        this.isSelected = isSelected;
    }

    private String isAvailable;

    public boolean getHasSelect() {
        return hasSelect;
    }

    public void setHasSelect(boolean hasSelect) {
        this.hasSelect = hasSelect;
    }

    public boolean isSelected() {
        return isSelected.get();
    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }
    public String getIsAvailable() {
        return isAvailable;
    }

    public Long getMedicineId(){ return medicineId; }
    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }


    public String getItemName(){ return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getExpirationDate(){ return expirationDate; }
    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

}


