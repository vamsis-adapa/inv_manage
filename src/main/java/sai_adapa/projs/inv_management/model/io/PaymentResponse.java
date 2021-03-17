package sai_adapa.projs.inv_management.model.io;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class PaymentResponse {

    @JsonProperty("amount_transferred")
    Integer amountTransferred;

    private String status;
    private LocalDateTime localDateTime;

    @JsonProperty("from_user_email")
    private Integer fromUserEmail;
    private Integer toUserEmail;
}
