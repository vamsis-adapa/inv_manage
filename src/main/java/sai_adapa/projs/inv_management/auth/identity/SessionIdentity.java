package sai_adapa.projs.inv_management.auth.identity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class SessionIdentity {
    private String email;

    public void setIdentity(String email) {
        this.email = email;
        System.out.println(this.email);
    }

    public Boolean verifyIdentity(String email) {

        if (email.equals(this.email)) {
            return true;
        }
        return false;
    }

}