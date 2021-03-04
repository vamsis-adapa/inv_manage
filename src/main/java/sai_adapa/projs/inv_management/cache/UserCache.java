package sai_adapa.projs.inv_management.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    public Users addUserFromSession(String Token, Users users) {
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


    @Caching(evict = {
            @CacheEvict(value = "email:session", key = "#eMail"),
            @CacheEvict(value = "session:user", key = "#token")
    })
    public void removeSession(String eMail, String token) {
        return;
    }

    @Caching(evict = {
            @CacheEvict(value = "email:user", key = "#users.email"),
            @CacheEvict(value = "session:user", key = "#users.sessionToken"),
            @CacheEvict(value = "uuid:user", key = "#users.userId")
    })
    public void invalidateCache(Users users) {
        return;
    }

}
