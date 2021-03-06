package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.cache.AdminCache;
import sai_adapa.projs.inv_management.exceptions.UserNotFoundException;
import sai_adapa.projs.inv_management.model.users.Admin;
import sai_adapa.projs.inv_management.repositories.sql.AdminRepository;
import sai_adapa.projs.inv_management.tools.PasswordTools;


@Service
public class AdminService {

    AdminRepository adminRepository;
    AdminCache adminCache;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    public void setAdminCache(AdminCache adminCache) {
        this.adminCache = adminCache;
    }

    public Boolean verifyToken(String token) {
        System.out.println(adminRepository.existsAdminBySessionToken(token));
        return adminRepository.existsAdminBySessionToken(token);
    }

    public void addUser(String email, String password) {
        adminRepository.save(Admin.builder().email(email).passwdHash(PasswordTools.encodePassword(password)).build());
    }


    public Admin getUser(String email) {
        Admin admin = adminCache.getAdmin(email);
        if (admin != null)
            return admin;
        admin = adminRepository.findByEmail(email);
        adminCache.addAdmin(email, admin);
        return admin;
    }

    public String getUserEmailFromSession(String token) throws UserNotFoundException {
        String email;
        try
        {email = adminCache.getEmailSession(token);}
        catch (IllegalArgumentException e)
        {
            email = null;
        }
        if (email != null)
            return email;

        try {
            email = adminRepository.findBySessionToken(token).getEmail();
            adminCache.addEmailSession(token, email);
        }catch (NullPointerException e)
        {
            email =null;
        }
        if (email==null)
            throw new UserNotFoundException();

        return email;
    }

    //add cache
    public Admin getUserFromSession(String token) {
        return adminRepository.findBySessionToken(token);
    }

    public void deleteUser(Admin admin) {
        adminRepository.delete(admin);
    }

    public Admin getReturnable(Admin admin) {
        admin.setPasswdHash(null);
        admin.setSessionToken(null);
        return admin;
    }

    public Admin displayUser(String email) {
        return getReturnable(getUser(email));
    }

    public void editUser(Admin admin, String email, String password) {
        int check = 0;
        if (email != null) {
            admin.setEmail(email);
            check = 1;
        }
        if (password != null) {
            admin.setPasswdHash(PasswordTools.encodePassword(password));
            check = 1;
        }
        if (check == 0) {
            //throw
        }
        adminRepository.save(admin);
    }


    public Boolean verifyUser(String email, String password) {
        String passwdHash = getUser(email).getPasswdHash();
        return PasswordTools.verifyPassword(password, passwdHash);
    }

    public String createSession(String email) {
        String token = adminCache.getSession(email);
        if (token != null) {
            return token;
        }
        Admin admin = getUser(email);
        String sessionToken = PasswordTools.generateNewToken();
        admin.setSessionToken(sessionToken);
        adminCache.addAdmin(email, admin);
        adminCache.addSession(email, admin.getSessionToken());
        adminRepository.save(admin);
        return sessionToken;
    }


    public void endSession(String email) {
        Admin admin = getUser(email);
        adminCache.removeAccess(admin.getSessionToken());
        admin.setSessionToken(null);
        adminCache.removeEmailSession(email);
        adminRepository.save(admin);
    }

}
