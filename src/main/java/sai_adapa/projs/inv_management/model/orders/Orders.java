package sai_adapa.projs.inv_management.model.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Orders {

    List<SubOrder> listOfItems;
    @Id
    private String Id;
    private Long orderGroup;
    private UUID vendorId;
    private UUID userId;
    private Long itemId;
    private LocalDateTime transactionDate;
    private Float totalCost;


}

