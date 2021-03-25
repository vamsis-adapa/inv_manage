package sai_adapa.projs.inv_management.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import sai_adapa.projs.inv_management.model.users.Vendor;

import java.util.UUID;

@Component
public class VendorCache {
    @Cacheable("email:vendor")
    public Vendor getUserFromEmail(String eMail) {
        return null;
    }

    @CachePut(value = "email:vendor", key = "#eMail")
    public Vendor addUserFromEmail(String eMail, Vendor vendor) {
        return vendor;
    }

    @Cacheable("uuid:vendor")
    public Vendor getUserFromUUID(UUID uuid) {
        return null;
    }

    @CachePut(value = "uuid:vendor", key = "#uuid")
    public Vendor addUserFromUUID(UUID uuid, Vendor vendor) {
        return vendor;
    }

    @Cacheable(value = "session:vendor")
    public Vendor getUserFromSession(String token) {
        return null;
    }

    @CachePut(value = "session:Vendor", key = "#token")
    public Vendor addUserFromSession(String token, Vendor vendor) {
        return vendor;
    }

    @Cacheable(value = "email:session")
    public String getSession(String eMail) {
        return null;
    }

    @CachePut(value = "email:session", key = "#eMail")
    public String addSession(String eMail, String session) {
        return session;
    }


    @CacheEvict(value = "session:vendor", key = "#token")
    public void removeSession(String token) {
        return;
    }

    @CacheEvict(value = "email:session", key = "#email")
    public void removeSessionToken(String email) {
        return;
    }

    @CacheEvict(value = "email:vendor", key = "#email")
    public void removeUserFromEmail(String email) {
        return;
    }

    @CacheEvict(value = "session:vendor", key = "#token")
    public void removeUserSession(String token) {
        return;
    }

    @CacheEvict(value = "uuid:vendor", key = "#userId")
    public void removeUserFromUUID(UUID userId) {
        return;
    }

}
