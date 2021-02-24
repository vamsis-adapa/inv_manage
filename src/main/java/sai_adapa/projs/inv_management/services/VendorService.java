package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.repositories.VendorRepository;
import sai_adapa.projs.inv_management.tools.AuthTools;

@Service
public class VendorService {
    VendorRepository vendorRepository;

    @Autowired
    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }


    public void addUser(String name, String email, String description, String passwd) {
        vendorRepository.save(Vendor.builder().name(name).email(email).description(description).passwdHash(AuthTools.encodePassword(passwd)).build());
    }

    public void deleteUser(Vendor vendor) {
        vendorRepository.delete(vendor);
    }

    public void editUser(Vendor vendor, String name, String email, String desc, String password) {
        int check = 0;
        if (name != null) {
            vendor.setName(name);
            check = 1;
        }
        if (email != null) {
            vendor.setEmail(email);
            check = 1;
        }
        if (desc != null) {
            vendor.setDescription(desc);
            check = 1;
        }
        if (password != null) {
            vendor.setPasswdHash(AuthTools.encodePassword(password));
            check = 1;
        }
        if ( check ==0)
        {
            //throw
        }
        vendorRepository.save(vendor);
    }


    public Vendor getUserBySession(String token) {
        return vendorRepository.findBySessionToken(token);
    }


    public Boolean verifySession(String token) {
        return vendorRepository.existsVendorBySessionToken(token);
    }

    public Vendor getUser(String email) {
        return vendorRepository.findByEmail(email);
    }


    public Boolean verifyUser(String email, String password) {
        return AuthTools.verifyPassword(password, getUser(email).getPasswdHash());
    }


    public String createSession(String email) {
        Vendor vendor = getUser(email);
        String sessionToken = AuthTools.generateNewToken();
        vendor.setSessionToken(sessionToken);
        vendorRepository.save(vendor);
        return sessionToken;
    }


    public void endSession(String email) {
        Vendor vendor = getUser(email);
        vendor.setSessionToken(null);
        vendorRepository.save(vendor);

    }
}
