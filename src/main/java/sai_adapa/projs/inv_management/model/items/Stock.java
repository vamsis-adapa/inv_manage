package sai_adapa.projs.inv_management.model.items;

import lombok.*;
import sai_adapa.projs.inv_management.model.users.Vendor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stock {

    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;
    int cost;
    @Id
    @GeneratedValue(generator = "identity")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    public Stock(Vendor vendor, Item item, int cost, Date dateAdded) {
        this.vendor = vendor;
        this.item = item;
        this.cost = cost;
        this.dateAdded = dateAdded;
    }


}
