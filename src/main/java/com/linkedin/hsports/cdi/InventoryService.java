package com.linkedin.hsports.cdi;

import com.linkedin.hsports.jpa.InventoryItem;

import java.io.Serializable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

public interface InventoryService extends Serializable {

    void createItem(Long catalogItemId, String name);

    Long getQuantity(Long catalogItemId);

    // ASYNC
    Future<InventoryItem> asyncGetQuantity(Long catalogItemId);

    // REACTIVE
    CompletionStage<InventoryItem> reactiveGetQuantity(Long catalogItemId);
}
