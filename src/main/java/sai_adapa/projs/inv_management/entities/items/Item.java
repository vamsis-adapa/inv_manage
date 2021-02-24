package sai_adapa.projs.inv_management.entities.items;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue( generator = "identity")
    private Long item_id;
    @Setter
    private String name;
    @Setter
    private String description;
}
