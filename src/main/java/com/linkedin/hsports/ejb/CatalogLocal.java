package com.linkedin.hsports.ejb;

import com.linkedin.hsports.jpa.CatalogItem;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CatalogLocal {

    List<CatalogItem> getItems();

    void addItem(CatalogItem item);

    CatalogItem findItem(Long itemID);

    void deleteItem(CatalogItem item);

    List<CatalogItem> searchByName(String name);

    public void saveItem(CatalogItem item);
}
