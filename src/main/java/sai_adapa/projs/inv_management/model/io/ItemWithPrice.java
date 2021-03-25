package sai_adapa.projs.inv_management.model.io;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemWithPrice {
    private String productName;
    private Double product_price;
}
