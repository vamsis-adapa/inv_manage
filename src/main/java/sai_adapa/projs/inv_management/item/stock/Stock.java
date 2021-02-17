package sai_adapa.projs.inv_management.item.stock;

import sai_adapa.projs.inv_management.item.Item;
import sai_adapa.projs.inv_management.users.vendor.Vendor;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Stock {
    @Id
    private String id;


    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name= "item_id")
    Item item;

    int cost;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;


    public Stock() {
    }

    public Stock(String id, Vendor vendor, Item item, int cost, Date dateAdded) {
        this.id = id;
        this.vendor = vendor;
        this.item = item;
        this.cost = cost;
        this.dateAdded = dateAdded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
