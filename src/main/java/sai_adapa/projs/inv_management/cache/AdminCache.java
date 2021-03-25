package sai_adapa.projs.inv_management.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import sai_adapa.projs.inv_management.model.users.Admin;

@Component
public class AdminCache {


    @Cacheable(value = "admin", key = "#email")
    public Admin getAdmin(String email) {
        System.out.println("hit failed");
        return null;
    }

    @CachePut(value = "admin", key = "#email")
    public Admin addAdmin(String email, Admin admin) {
        return admin;
    }

    @Cacheable(value = "admin_session", key = "#email")
    public String getSession(String email) {
        return null;
    }

    @CachePut(value = "admin_session", key = "#email")
    public String addSession(String email, String token) {
        return token;
    }

    @Cacheable(value = "admin_access", key = "#token")
    public String getEmailSession(String token) {
        return null;
    }

    @CachePut(value = "admin_access", key = "#token")
    public String addEmailSession(String token, String email) {
        return email;
    }

    @CacheEvict(value = "admin_access", key = "#token")
    public void removeAccess(String token) {
        return;
    }

    @CacheEvict(value = "admin_session", key = "#email")
    public void removeEmailSession(String email) {
        return;
    }

    @CacheEvict(value = "admin", key = "#email")
    public void removeAdmin(String email) {
        return;
    }

}
