package com.linkedin.hsports.jsf.backingbeans;
;
import com.linkedin.hsports.cdi.InventoryService;
import com.linkedin.hsports.cdi.qualifiers.RemoteService;
import com.linkedin.hsports.ejb.CatalogLocal;
import com.linkedin.hsports.jpa.CatalogItem;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
//@SessionScoped    // This scope isn't needed anymore as we now have persistence dealing with CatalogItem from a db
@RequestScoped
public class CatalogItemFormBean implements Serializable {

//    @EJB
    @Inject         // Preferred over @EJB
    private CatalogLocal catalogBean;

//    @Named("remoteInventoryService")       // Qualifier
    @Inject
    @RemoteService                         // Custom Qualifier
    private InventoryService inventoryService;

    private CatalogItem item = new CatalogItem();
    private List<CatalogItem> items = new ArrayList<>();
    private String searchText;

    public void init() {
        this.items = this.catalogBean.getItems();
    }

    public String addItem() {

        this.catalogBean.addItem(this.item);

//        this.catalogBean.getItems().stream().forEach(item -> System.out.println(item.toString()));
        this.inventoryService.createItem(this.item.getItemId(), this.item.getName());

        return "list?faces-redirect=true";
    }

    public void searchByName() {
        this.items = this.catalogBean.searchByName(this.searchText);
    }

    public CatalogItem getItem() {
        return item;
    }

    public void setItem(CatalogItem item) {
        this.item = item;
    }

    public List<CatalogItem> getItems() {
        return items;
    }

    public void setItems(List<CatalogItem> items) {
        this.items = items;
    }

    public CatalogLocal getCatalogBean() {
        return catalogBean;
    }

    public void setCatalogBean(CatalogLocal catalogBean) {
        this.catalogBean = catalogBean;
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
