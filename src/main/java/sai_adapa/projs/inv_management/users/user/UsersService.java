package sai_adapa.projs.inv_management.users.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.auth.AuthTools;

@Service
public class UsersService {

    UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void addUser(PreUsers preUsers) {
        Users users = new Users(preUsers.getName(), preUsers.getEmail(), preUsers.getDetails(), AuthTools.encodePassword(preUsers.getPasswd()));
        usersRepository.save(users);
    }

    public void addUser(String name, String e_mail, String details, String password) {
        Users users = new Users(name, e_mail, details, AuthTools.encodePassword(password));
        usersRepository.save(users);
    }

    public Users getUser(String e_mail) {
        return usersRepository.findByEmail(e_mail);
    }

    @Cacheable(value = "usersFromSession", key = "#token")
    public Users getUsersBySession(String token) {
        return usersRepository.findBySessionToken(token);
    }

    @Cacheable(value = "usersSessionStatus", key = "#token")
    public Boolean verifySession(String token) {
        return usersRepository.existsUsersBySessionToken(token);
    }

    public Boolean verifyUser(Users users, String password) {
        return AuthTools.verifyPassword(password, users.getPasswdHash());
    }

    public void deleteUser(Users users) {
        usersRepository.delete(users);
    }

    public void editUser(Users users, String name, String email, String details, String password) {
        if (name != null)
            users.setName(name);
        if (email != null)
            users.setEmail(email);
        if (details != null)
            users.setDetails(details);
        if (password != null)
            users.setPasswdHash(AuthTools.encodePassword(password));
        usersRepository.save(users);
    }

    public Boolean verifyUser(String e_mail, String password) {
        String passwdHash = getUser(e_mail).getPasswdHash();
        return AuthTools.verifyPassword(password, passwdHash);
    }

    public String createSession(Users users) {
        String sessionToken = AuthTools.generateNewToken();
        users.setSessionToken(sessionToken);
        usersRepository.save(users);
        return sessionToken;

    }

    public String createSession(String e_mail) {
        Users users = getUser(e_mail);
        String sessionToken = AuthTools.generateNewToken();
        users.setSessionToken(sessionToken);
        usersRepository.save(users);
        return sessionToken;
    }

    public void endSession(Users users) {
        users.setSessionToken(null);
        usersRepository.save(users);
    }

    public void endSession(String e_mail) {
        Users users = getUser(e_mail);
        users.setSessionToken(null);
        usersRepository.save(users);
    }

}
