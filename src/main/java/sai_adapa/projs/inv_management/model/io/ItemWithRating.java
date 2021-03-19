package sai_adapa.projs.inv_management.model.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.items.Rating;
import sai_adapa.projs.inv_management.model.users.Users;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ItemWithRating {
    private final String type;
    private Long clientId;
    private String clientName;
    private String clientPassword;
    private Long typeId = null;
    private Map<String, Integer> rating = new HashMap<>();
    private String name;
    {
        type = "Inventory";
    }

    public ItemWithRating(Item item, Rating rating, Users user) {
        this.clientId = user.getRating_id();
        this.clientName = user.getEmail();
        this.clientPassword = user.getRating_id().toString();
        this.typeId = item.getItem_id();
        this.name = item.getName();
        this.rating.put("quality",rating.getQuality());
        this.rating.put("asAdvertised", rating.getAsAdvertised());
        this.rating.put("satisfaction", rating.getSatisfaction());
    }
}


