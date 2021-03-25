package sai_adapa.projs.inv_management.model.io;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreUserWithOrder extends PreUsers {

    private String vendorEmail;
    private Long itemId;
    private Integer numberOfItems;

}
