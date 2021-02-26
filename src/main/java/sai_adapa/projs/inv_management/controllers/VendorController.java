package sai_adapa.projs.inv_management.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.users.io.PreVendor;
import sai_adapa.projs.inv_management.model.users.io.PreVendorWithItem;
import sai_adapa.projs.inv_management.services.VendorService;

import java.util.UUID;

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
        vendorService.addUser(preVendor.getName(),preVendor.getEmail(),preVendor.getDescription(),preVendor.getPasswd());
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


    @RequestMapping(method = RequestMethod.PUT, value = {"/vendor/items"})
    public void addItem(@RequestBody PreVendorWithItem preVendorWithItem )
    {
        vendorService.addNewItem(preVendorWithItem.getItem_name(), preVendorWithItem.getItem_description());
    }
    @RequestMapping(method = RequestMethod.PUT, value = {"/vendor/stock"})
    public void addStock(@RequestBody PreVendorWithItem preVendorWithItem  )
    {
        vendorService.addStock(preVendorWithItem.getEmail(), preVendorWithItem.getItem_id(),preVendorWithItem.getCost(), preVendorWithItem.getNum_stock());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/vendor/stock"})
    public void editStock(@RequestBody PreVendorWithItem preVendorWithItem  )
    {
        vendorService.editStock(preVendorWithItem.getEmail(),preVendorWithItem.getItem_id(),preVendorWithItem.getNum_stock(),preVendorWithItem.getCost());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/vendor/stock"})
    public void deleteStock(@RequestBody PreVendorWithItem preVendorWithItem  )
    {
        vendorService.deleteStock(preVendorWithItem.getEmail(),preVendorWithItem.getItem_id());
    }
    







}
