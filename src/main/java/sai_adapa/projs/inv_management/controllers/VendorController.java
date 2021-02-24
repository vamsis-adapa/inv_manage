package sai_adapa.projs.inv_management.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sai_adapa.projs.inv_management.entities.users.io.PreVendor;
import sai_adapa.projs.inv_management.services.VendorService;

@RestController
public class VendorController {

    VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/vendor/logout"})
    public void signOut(@RequestBody PreVendor preVendor) {
        vendorService.endSession(preVendor.getEmail());
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/new"})
    public void signUp(@RequestBody PreVendor preVendor) {
        vendorService.addUser(preVendor);
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/login"})
    public String signIn(@RequestBody PreVendor preVendor) {
        if (vendorService.verifyUser(preVendor.getEmail(), preVendor.getPasswd())) {
            return vendorService.createSession(preVendor.getEmail());
        } else
            return "failed";
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/vendor"})
    public void editVendor(@RequestBody PreVendor preVendor)
    {
        vendorService.editUser(vendorService.getUser(preVendor.getEmail()),preVendor.getName(),preVendor.getEmail(),preVendor.getDescription(),preVendor.getPasswd());
    }








}
