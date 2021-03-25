package sai_adapa.projs.inv_management.model.io;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ItemDetails {

    String name;
    String Details;
    List<VendorCostMap> listOfVendors;


}


