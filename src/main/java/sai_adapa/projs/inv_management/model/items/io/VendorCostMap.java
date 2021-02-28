package sai_adapa.projs.inv_management.model.items.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VendorCostMap{
        String vendor_email;
        Double cost;
        Integer numberOfItemsInStock;

        }