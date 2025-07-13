package com.linkedin.hsports.cdi.factory;

import com.linkedin.hsports.cdi.InventoryService;
import com.linkedin.hsports.cdi.LocalInventoryService;
import com.linkedin.hsports.cdi.RemoteInventoryService;

import java.time.LocalDateTime;

public class InventoryServiceFactory {

//    @Produces         // Removed to allow intercepting only the LocalInventoryService implementation class
    private InventoryService createInstance () {
        InventoryService inventoryService = null;

        if (LocalDateTime.now().getHour() >= 12) {
            inventoryService = new LocalInventoryService();
        } else {
            inventoryService = new RemoteInventoryService();
        }
        return inventoryService;
    }
}
