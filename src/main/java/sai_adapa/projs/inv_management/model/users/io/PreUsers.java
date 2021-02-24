package sai_adapa.projs.inv_management.model.users.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreUsers {
    private String name;
    private String email;
    private String details;
    private String passwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public PreUsers(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }




}
