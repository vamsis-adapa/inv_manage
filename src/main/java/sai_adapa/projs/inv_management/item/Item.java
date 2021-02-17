package sai_adapa.projs.inv_management.item;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    private String item_id;
    private String name;
    private String description;

    public Item() {
    }

    public Item(String item_id, String name, String description) {
        this.item_id = item_id;
        this.name = name;
        this.description = description;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String id) {
        this.item_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
