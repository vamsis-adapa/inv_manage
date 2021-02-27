package sai_adapa.projs.inv_management.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.model.users.io.PreVendor;
import sai_adapa.projs.inv_management.model.users.io.PreVendorWithItem;
import sai_adapa.projs.inv_management.services.VendorService;

import java.util.List;

@RestController
public class VendorController {

    VendorService vendorService;
    SessionIdentity sessionIdentity;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setEmailVerifier(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @Autowired
    public void setSessionIdentity(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/vendor/logout"})
    public void signOut(@RequestBody PreVendor preVendor) {
        if (!sessionIdentity.verifyIdentity(preVendor.getEmail())) {
            // throw
            return;
        }
        vendorService.endSession(preVendor.getEmail());
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/new"})
    public void signUp(@RequestBody PreVendor preVendor) {
        vendorService.addUser(preVendor.getName(), preVendor.getEmail(), preVendor.getDescription(), preVendor.getPasswd());
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/login"})
    public String signIn(@RequestBody PreVendor preVendor) {
        if (vendorService.verifyUser(preVendor.getEmail(), preVendor.getPasswd())) {
            return vendorService.createSession(preVendor.getEmail());
        } else
            return "failed";
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/vendor"})
    public void editVendor(@RequestBody PreVendor preVendor) {
        if (!sessionIdentity.verifyIdentity(preVendor.getEmail())) {
            // throw
            return;
        }
        vendorService.editUser(vendorService.getUser(preVendor.getEmail()), preVendor.getName(), preVendor.getChanged_email(), preVendor.getDescription(), preVendor.getPasswd());
    }


    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/items"})
    public void addItem(@RequestBody PreVendorWithItem preVendorWithItem) {
        vendorService.addNewItem(preVendorWithItem.getItem_name(), preVendorWithItem.getItem_description());
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/stock"})
    public void addStock(@RequestBody PreVendorWithItem preVendorWithItem) {
        if (!sessionIdentity.verifyIdentity(preVendorWithItem.getEmail())) {
            // throw
            return;
        }
        vendorService.addStock(preVendorWithItem.getEmail(), preVendorWithItem.getItem_id(), preVendorWithItem.getCost(), preVendorWithItem.getNum_stock());
    }

    @RequestMapping(method = RequestMethod.GET,value = {"/vendor"})
    public Vendor displayVendor() {
        return vendorService.displayUser(sessionIdentity.getEmail());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/vendor/stock"})
    public void editStock(@RequestBody PreVendorWithItem preVendorWithItem) {
        if (!sessionIdentity.verifyIdentity(preVendorWithItem.getEmail())) {
            // throw
            return;
        }
        vendorService.editStock(preVendorWithItem.getEmail(), preVendorWithItem.getItem_id(), preVendorWithItem.getNum_stock(), preVendorWithItem.getCost());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/vendor/stock"})
    public void deleteStock(@RequestBody PreVendorWithItem preVendorWithItem) {
        if (!sessionIdentity.verifyIdentity(preVendorWithItem.getEmail())) {
            // throw
            return;
        }
        vendorService.deleteStock(preVendorWithItem.getEmail(), preVendorWithItem.getItem_id());
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/vendor/stock"})
    public List<Stock> viewStock(@RequestBody PreVendorWithItem preVendorWithItem) {
        if (!sessionIdentity.verifyIdentity(preVendorWithItem.getEmail())) {
            // throw
            return null;
        }
        return vendorService.getVendorStock(preVendorWithItem.getEmail());
    }


}
