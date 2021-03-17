package sai_adapa.projs.inv_management.model.items;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sai_adapa.projs.inv_management.model.users.Vendor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(nullable = false)
    Integer inv_num;
    @Column(nullable = false)
    Double cost;
    @Id
    @GeneratedValue(generator = "identity")
    @Column(nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
    @CreationTimestamp
    private LocalDateTime DateAdded;
    @UpdateTimestamp
    private LocalDateTime DateUpdated;


}
