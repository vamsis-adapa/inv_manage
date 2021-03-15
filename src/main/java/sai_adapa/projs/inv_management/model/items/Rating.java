package sai_adapa.projs.inv_management.model.items;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Rating {
    private int quality;
    private int asAdvertised;
    private int satisfaction;

}
