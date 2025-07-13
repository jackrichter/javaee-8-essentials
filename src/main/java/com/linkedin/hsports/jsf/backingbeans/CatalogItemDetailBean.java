package com.linkedin.hsports.jsf.backingbeans;

import com.linkedin.hsports.cdi.InventoryService;
import com.linkedin.hsports.cdi.qualifiers.RemoteService;
import com.linkedin.hsports.ejb.CatalogLocal;
import com.linkedin.hsports.jpa.CatalogItem;
import com.linkedin.hsports.jpa.InventoryItem;
import com.linkedin.hsports.jpa.ItemManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Named
@ConversationScoped
public class CatalogItemDetailBean implements Serializable {

    private Long itemId;
    private CatalogItem item;
    private Long quantity;

    @Inject
    @RemoteService
    private InventoryService inventoryService;

    @Inject
    private Conversation conversation;

    @Inject
    private CatalogLocal catalogBean;

    private ItemManager manager = new ItemManager();

    @PostConstruct
    public void init() {
        this.conversation.begin();
    }

    @PreDestroy
    public void destroy() {
        this.conversation.end();
    }

    public void fetchItem() {
        this.item = this.catalogBean.findItem(this.itemId);
        this.quantity = this.inventoryService.getQuantity(this.itemId);
    }

    public void fetchItemAsync() throws ExecutionException, InterruptedException {
        this.item = this.catalogBean.findItem(this.itemId);

        Future<InventoryItem> future = this.inventoryService.asyncGetQuantity(this.itemId);
        System.out.println("Doing other work");

        this.quantity = future.get().getQuantity();
        System.out.println("Completed request");
    }

    public void fetchItemReactive() throws InterruptedException {
        this.item = this.catalogBean.findItem(this.itemId);

        CountDownLatch latch = new CountDownLatch(1);

        this.inventoryService.reactiveGetQuantity(this.itemId)
                .thenApply(item -> item.getQuantity())
                .thenAccept(quantity -> {
                    this.setQuantity(quantity);
                    latch.countDown();
                    System.out.println("Quantity lached: " + this.getQuantity());
                });

        System.out.println("Completed request");
        latch.await();
    }

    public void addManager() {
        this.manager.getCatalogItems().add(this.item);
        this.item.getItemManagers().add(this.manager);

        this.catalogBean.saveItem(this.item);
        this.item = this.catalogBean.findItem(this.itemId);
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public CatalogItem getItem() {
        return item;
    }

    public void setItem(CatalogItem item) {
        this.item = item;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public ItemManager getManager() {
        return manager;
    }

    public void setManager(ItemManager manager) {
        this.manager = manager;
    }
}
