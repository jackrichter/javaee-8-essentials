package com.linkedin.hsports.ejb;

import com.linkedin.hsports.jpa.CatalogItem;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Singleton
@LocalBean
public class CatalogLocalService implements CatalogLocal, Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    private List<CatalogItem> items = new ArrayList<CatalogItem>();

    public CatalogLocalService() {
    }

    @Override
    public List<CatalogItem> getItems() {
        return this.entityManager.createQuery("SELECT c FROM  CatalogItem c", CatalogItem.class).getResultList();
    }

    @Override
    public void addItem(CatalogItem item) {

        this.entityManager.persist(item);
    }

    @Override
    public CatalogItem findItem(Long itemID) {
        return this.entityManager.find(CatalogItem.class, itemID);
    }

    @Override
    public void deleteItem(CatalogItem item) {
        this.entityManager.remove(this.entityManager.contains(item) ? item : this.entityManager.merge((item)));
    }

    @Override
    public List<CatalogItem> searchByName(String name) {
        return this.entityManager.createQuery("SELECT c FROM CatalogItem c" +
                " WHERE c.name LIKE :name", CatalogItem.class)
                .setParameter("name", "%" + name + "%").getResultList();
    }

    @Override
    public void saveItem(CatalogItem item) {
        this.entityManager.merge(item);
    }
}
