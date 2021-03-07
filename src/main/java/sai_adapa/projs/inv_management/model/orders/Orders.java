package sai_adapa.projs.inv_management.model.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import sai_adapa.projs.inv_management.tools.enums.OrderStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document
@Entity
public class Orders {
    @Id
    private String Id;
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



