package sai_adapa.projs.inv_management.users.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.auth.AuthTools;

@Service
public class VendorService {
    VendorRepository vendorRepository;

    @Autowired
    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public void addUser(PreVendor preVendor) {
        vendorRepository.save(new Vendor(preVendor.getName(), preVendor.getEmail(), preVendor.getDescription(), AuthTools.encodePassword(preVendor.getPasswd())));
    }

    public void addUser(String name, String email, String description, String passwd) {
        vendorRepository.save(new Vendor(name, email, description, AuthTools.encodePassword(passwd)));
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

    public Boolean verifyUser(Vendor vendor, String password) {
        return AuthTools.verifyPassword(password, vendor.getPasswdHash());
    }

    public Boolean verifyUser(String email, String password) {
        return AuthTools.verifyPassword(password, getUser(email).getPasswdHash());
    }

    public String createSession(Vendor vendor) {
        String sessionToken = AuthTools.generateNewToken();
        vendor.setSessionToken(sessionToken);
        vendorRepository.save(vendor);
        return sessionToken;
    }

    public String createSession(String email) {
        Vendor vendor = getUser(email);
        String sessionToken = AuthTools.generateNewToken();
        vendor.setSessionToken(sessionToken);
        vendorRepository.save(vendor);
        return sessionToken;
    }

    public void endSession(Vendor vendor) {
        vendor.setSessionToken(null);
        vendorRepository.save(vendor);
    }

    public void endSession(String email) {
        Vendor vendor = getUser(email);
        vendor.setSessionToken(null);
        vendorRepository.save(vendor);

    }
}