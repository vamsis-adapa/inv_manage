package sai_adapa.projs.inv_management.model.items.stock;

import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.users.Vendor;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Stock {

    @Id
    @GeneratedValue( generator = "identity")
    private Long id;


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

    public Stock( Vendor vendor, Item item, int cost, Date dateAdded) {
        this.vendor = vendor;
        this.item = item;
        this.cost = cost;
        this.dateAdded = dateAdded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
