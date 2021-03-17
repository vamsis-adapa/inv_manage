package sai_adapa.projs.inv_management.model.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.items.Rating;

@Getter
@Setter
@NoArgsConstructor
public class ItemWithRating {
    private final String type;
    private String name;
    private Long typeid;
    private Rating rating;
    private String token;

    {
        type = "Inventory";
    }

    public ItemWithRating(Item item, Rating rating) {
        this.typeid = item.getItem_id();
        this.name = item.getName();
        this.rating = rating;
    }
}


