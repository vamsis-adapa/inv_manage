package sai_adapa.projs.inv_management.auth.identity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
@Getter
@Setter
public class SessionIdentity {
    private String email;

    public void setIdentity(String email)
    {
        this.email = email;
    }

    public Boolean verifyIdentity(String email) {
        if (email == this.email) {
            return true;
        }
        return false;
    }

}