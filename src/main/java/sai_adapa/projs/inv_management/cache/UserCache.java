package sai_adapa.projs.inv_management.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import sai_adapa.projs.inv_management.model.users.Users;

import java.util.UUID;

@Component
public class UserCache {

    @Cacheable("email:user")
    public Users getUserFromEmail(String eMail) {
        return null;
    }

    @CachePut(value = "email:user", key = "#eMail")
    public Users addUserFromEmail(String eMail, Users users) {
        return users;
    }

    @Cacheable("uuid:user")
    public Users getUserFromUUID(UUID uuid) {
        return null;
    }

    @CachePut(value = "uuid:user", key = "#uuid")
    public Users addUserFromUUID(UUID uuid, Users users) {
        return users;
    }

    @Cacheable(value = "session:user")
    public Users getUserFromSession(String token) {
        return null;
    }

    @CachePut(value = "session:user", key = "#token")
    public Users addUserFromSession(String token, Users users) {
        return users;
    }

    @Cacheable(value = "email:session")
    public String getSession(String eMail) {
        return null;
    }

    @CachePut(value = "email:session", key = "#eMail")
    public String addSession(String eMail, String session) {
        return session;
    }


    @CacheEvict(value = "session:user", key = "#token")
    public void removeSession(String token) {
        return;
    }

    @CacheEvict(value = "email:session", key = "#email")
    public void removeSessionToken(String email) {
        return;
    }

    @CacheEvict(value = "email:user", key = "#email")
    public void removeUserFromEmail(String email) {
        return;
    }

    @CacheEvict(value = "session:user", key = "#token")
    public void removeUserSession(String token) {
        return;
    }

    @CacheEvict(value = "uuid:user", key = "#userId")
    public void removeUserFromUUID(UUID userId) {
        return;
    }

}
