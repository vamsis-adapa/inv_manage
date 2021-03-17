package sai_adapa.projs.inv_management.model.io;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailsAndAmount {
    private String email;
    private String secondEmail;
    private Integer amount;
}
