package sai_adapa.projs.inv_management.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import sai_adapa.projs.inv_management.model.users.Admin;

@Component
public class AdminCache {


    @Cacheable("admin")
    public Admin getAdmin(String email) {
        return null;
    }

    @CachePut(value = "admin", key = "#email")
    public Admin addAdmin(String email, Admin admin) {
        return admin;
    }

    @Cacheable("admin_session")
    public String getSession(String email) {
        return null;
    }

    @CachePut(value = "admin_session", key = "#email")
    public String addSession(String email, String token) {
        return token;
    }

    @Cacheable("admin_access")
    public String getEmail(String token) {
        return null;
    }

    @CachePut(value = "admin_access", key = "token")
    public String addEmail(String token, String email) {
        return email;
    }

    @CacheEvict(value = "admin_access")
    public void removeAccess(String token) {
        return;
    }

    @CacheEvict(value = "admin_session")
    public void removeSession(String email) {
        return;
    }

    @CacheEvict(value = "admin")
    public void removeAdmin(String email) {
        return;
    }

}
