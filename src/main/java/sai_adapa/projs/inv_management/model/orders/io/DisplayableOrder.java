package sai_adapa.projs.inv_management.model.orders.io;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import sai_adapa.projs.inv_management.model.orders.Orders;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisplayableOrder {
    private String id;
    private String  vendorEmail;
    private String userEmail;
    private Long itemId;
    private String itemName;
    private Integer numberOfItems;
    private Double individualCost;
    private LocalDateTime transactionDate;
    private Double totalCost;


}
