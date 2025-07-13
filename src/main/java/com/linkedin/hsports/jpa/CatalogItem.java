package com.linkedin.hsports.jpa;

import com.linkedin.hsports.validations.Alphabetic;
import com.linkedin.hsports.validations.PermittedManufacturer;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATALOG_ITEM")
public class CatalogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CATALOG_ITEM_ID")
    private Long itemId;

    @NotBlank
    @Column(name = "NAME")
    private String name;

//    @Pattern(regexp = "^[A-Za-z]*$", message = "Must be letters")
    @Alphabetic             // A custom validator
    @PermittedManufacturer
    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Pattern(regexp = "^[A-Za-z]{5,10}$", message = "Must be letters")
    @Column(name = "DESCRIPTION")
    private String description;

    @Future
    @Column(name = "AVAILABLE_DATE")
    private LocalDate availableDate;

    @Size(min = 0, max = 3)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "CATALOG_ITEM_ID"), inverseJoinColumns = @JoinColumn(name = "ITEM_MANAGER_ID"))
    private List<ItemManager> itemManagers = new ArrayList<ItemManager>();

    public CatalogItem() {
    }

    public CatalogItem(String name, String manufacturer, String description, LocalDate availableDate) {
        super();
        this.name = name;
        this.manufacturer = manufacturer;
        this.description = description;
        this.availableDate = availableDate;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public List<ItemManager> getItemManagers() {
        return itemManagers;
    }

    public void setItemManagers(List<ItemManager> itemManagers) {
        this.itemManagers = itemManagers;
    }

    @Override
    public String toString() {
        return "CatalogItem [itemId=" + itemId + ", name=" + name + ", manufacturer=" + manufacturer + ", description="
                + description + ", availableDate=" + availableDate + "]";
    }
}
