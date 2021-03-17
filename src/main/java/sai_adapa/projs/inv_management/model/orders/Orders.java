package sai_adapa.projs.inv_management.model.orders;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import sai_adapa.projs.inv_management.model.enums.OrderStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
@Entity
public class Orders {
    String PaymentID;
    @Id
    private String id;
    private UUID vendorId;
    private UUID userId;
    private Long itemId;
    private Integer numberOfItems;
    private Double individualCost;
    private OrderStatus orderStatus;
    @CreatedDate
    private LocalDateTime transactionDate;
    private Double totalCost;


}



