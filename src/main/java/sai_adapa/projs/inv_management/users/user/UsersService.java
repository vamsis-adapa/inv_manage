package sai_adapa.projs.inv_management.users.user;

import org.springframework.beans.factory.annotation.Autowired;
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
        Users users = new Users(preUsers.getName(), preUsers.getE_mail(), preUsers.getDetails(), AuthTools.encodePassword(preUsers.getPasswd()));
        usersRepository.save(users);
    }

    public void addUser(String name, String e_mail, String details, String password) {
        Users users = new Users(name, e_mail, details, AuthTools.encodePassword(password));
        usersRepository.save(users);
    }

    public Users getUser(String e_mail) {
        return usersRepository.findByEmail(e_mail).get(0);
    }

    public Boolean verifyUser(Users users, String password) {
        return AuthTools.verifyPassword(password, users.getPasswdHash());
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
