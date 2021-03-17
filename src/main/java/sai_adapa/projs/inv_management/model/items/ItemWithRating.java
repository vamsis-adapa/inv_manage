package sai_adapa.projs.inv_management.model.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemWithRating extends Item {
    protected Rating rating;
    protected String token;

    public ItemWithRating(Item item, Rating rating) {
        this.item_id = item.getItem_id();
        this.name = item.getName();
        this.rating = rating;
    }
}


