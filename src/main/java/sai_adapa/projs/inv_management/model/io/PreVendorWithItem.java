package sai_adapa.projs.inv_management.model.io;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PreVendorWithItem extends PreVendor {

    private Long item_id;

    private String item_name;

    private String item_description;
    private Double cost;
    private Integer num_stock;

}
