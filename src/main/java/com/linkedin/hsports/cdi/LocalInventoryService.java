package com.linkedin.hsports.cdi;

import com.linkedin.hsports.cdi.interceptor.Logging;
import com.linkedin.hsports.jpa.InventoryItem;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

@ApplicationScoped
// Removed to allow intercepting in this class
//@Alternative                            // Factory
public class LocalInventoryService implements InventoryService {

    private Map<Long, InventoryItem> inventoryItems = new HashMap<Long, InventoryItem>();

    @Override
    @Logging            // Intercepting att this method
    public void createItem(Long catalogItemId, String name) {
        long inventoryItemId = this.inventoryItems.size() + 1;
        this.inventoryItems.put(inventoryItemId,new InventoryItem(inventoryItemId, catalogItemId, name, 0L));
        this.printInventory();
    }

    @Override
    public Long getQuantity(Long catalogItemId) {
        return 0L;
    }

    @Override
    public Future<InventoryItem> asyncGetQuantity(Long catalogItemId) {
        return null;
    }

    @Override
    public CompletionStage<InventoryItem> reactiveGetQuantity(Long catalogItemId) {
        return null;
    }

    private void printInventory() {
        System.out.println("Local inventory contains: ");

        for (Map.Entry<Long, InventoryItem> entry : inventoryItems.entrySet()) {
            System.out.println(entry.getValue().getName());
        }
    }
}
