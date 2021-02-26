package sai_adapa.projs.inv_management.model.items;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;
import sai_adapa.projs.inv_management.model.users.Vendor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    Integer inv_num;
    Double cost;
    @Id
    @GeneratedValue(generator = "identity")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @CreationTimestamp
    private LocalDateTime DateAdded;
    @UpdateTimestamp
    private  LocalDateTime DateUpdated;


    public Stock(Vendor vendor, Item item, Double cost,Integer inv_num) {
        this.vendor = vendor;
        this.item = item;
        this.cost = cost;
        this.inv_num = inv_num;
    }


}
