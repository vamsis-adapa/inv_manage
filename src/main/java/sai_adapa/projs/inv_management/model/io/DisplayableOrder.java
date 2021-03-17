package sai_adapa.projs.inv_management.model.io;

import lombok.*;

import java.time.LocalDateTime;

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
