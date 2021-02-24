package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.tools.AuthTools;
import sai_adapa.projs.inv_management.repositories.AdminRepository;
import sai_adapa.projs.inv_management.entities.users.Admin;


@Service
public class AdminService {

    AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    public Boolean verifyToken(String token) {
        System.out.println(adminRepository.existsAdminBySessionToken(token));
        return adminRepository.existsAdminBySessionToken(token);
    }

    public void addUser(String email, String password) {
        adminRepository.save( Admin.builder().email(email).passwdHash(AuthTools.encodePassword(password)).build());
    }

    public Admin getUser(String email) {
        return adminRepository.findByEmail(email);
    }


    public Admin getUserFromSession(String token) {
        return adminRepository.findBySessionToken(token);
    }

    public void deleteUser(Admin admin) {
        adminRepository.delete(admin);
    }

    public void editUser(Admin admin, String email, String password) {
        if (email != null)
            admin.setEmail(email);
        if (password != null)
            admin.setPasswdHash(AuthTools.encodePassword(password));
        adminRepository.save(admin);
    }

    public Boolean verifyUser(Admin admin, String password) {
        return AuthTools.verifyPassword(password, admin.getPasswdHash());
    }

    public Boolean verifyUser(String email, String password) {
        String passwdHash = getUser(email).getPasswdHash();
        return AuthTools.verifyPassword(password, passwdHash);
    }

    public String createSession(Admin admin) {
        String sessionToken = AuthTools.generateNewToken();
        admin.setSessionToken(sessionToken);
        adminRepository.save(admin);
        return sessionToken;
    }


    public String createSession(String email) {
        Admin admin = getUser(email);
        String sessionToken = AuthTools.generateNewToken();
        admin.setSessionToken(sessionToken);
        adminRepository.save(admin);
        return sessionToken;
    }

    public void endSession(Admin admin) {
        admin.setSessionToken(null);
        adminRepository.save(admin);
    }

    public void endSession(String email) {
        Admin admin = getUser(email);
        admin.setSessionToken(null);
        adminRepository.save(admin);
    }

}