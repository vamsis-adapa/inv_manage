package sai_adapa.projs.inv_management.model.io;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisplayableOrderVendor {
    private String id;
    private String vendorEmail;
    private UUID userID;
    private Long itemId;
    private String itemName;
    private Integer numberOfItems;
    private Double individualCost;
    private LocalDateTime transactionDate;
    private Double totalCost;
}
