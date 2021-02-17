package sai_adapa.projs.inv_management.users.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.auth.AuthTools;

@Service
public class AdminService {

    AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void addUser(PreAdmin preAdmin) {
        adminRepository.save(new Admin(preAdmin.getEmail(), AuthTools.encodePassword(preAdmin.getPasswd())));
    }

    public void addUser(String email, String password) {
        adminRepository.save(new Admin(email, AuthTools.encodePassword(password)));
    }

    public Admin getUser(String email) {
        return adminRepository.findByEmail(email);
    }

    public Boolean verifyUser(Admin admin, String password) {
        return AuthTools.verifyPassword(password, admin.getPasswdHash());
    }

    public Boolean verifyUser(String email, String password) {
        String passwdHash = getUser(email).getPasswdHash();
        return AuthTools.verifyPassword(password, passwdHash);
    }

    public String createSession(Admin admin)
    {
        String sessionToken = AuthTools.generateNewToken();
        admin.setSessionToken(sessionToken);
        adminRepository.save(admin);
        return  sessionToken;
    }
    public String createSession(String email) {
        Admin admin = getUser(email);
        String sessionToken = AuthTools.generateNewToken();
        admin.setSessionToken(sessionToken);
        adminRepository.save(admin);
        return sessionToken;
    }

    public void endSession(Admin admin)
    {
        admin.setSessionToken(null);
        adminRepository.save(admin);
    }
    public  void endSession (String email)
    {
        Admin admin = getUser(email);
        admin.setSessionToken(null);
        adminRepository.save(admin);
    }

}
