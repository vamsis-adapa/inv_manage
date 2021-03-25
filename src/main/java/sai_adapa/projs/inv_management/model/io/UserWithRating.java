package sai_adapa.projs.inv_management.model.io;

import lombok.*;
import org.springframework.context.annotation.Bean;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.items.Rating;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithRating {

    String email;
    Rating rating;
    Long itemId;
}
