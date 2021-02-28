package sai_adapa.projs.inv_management.model.users.io;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PreUserWithOrder extends PreUsers {

    private String vendorEmail;
    private Long itemId;
    private Integer numberOfItems;

}
