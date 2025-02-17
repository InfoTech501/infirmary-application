package com.rocs.medical.records.application.app.facade;
import com.rocs.medical.records.application.model.Inventory.Inventory;
import java.util.List;

public interface InventoryFacade {


    List<Inventory> getInventoryItems();

}
