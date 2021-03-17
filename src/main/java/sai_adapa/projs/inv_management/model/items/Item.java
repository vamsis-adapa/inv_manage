package sai_adapa.projs.inv_management.model.items;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

    @Id
    @GeneratedValue(generator = "identity")
    protected Long item_id;
    @Setter
    @Column(unique = true)
    protected String name;
    @Setter
    protected String description;

}
