package sai_adapa.projs.inv_management.model.users.io;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreUsers {
    private String name;
    private String email;
    private String changed_email;
    private String details;
    private String passwd;


    public PreUsers(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }




}
