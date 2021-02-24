package sai_adapa.projs.inv_management.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sai_adapa.projs.inv_management.services.AdminService;
import sai_adapa.projs.inv_management.entities.users.io.PreAdmin;
import sai_adapa.projs.inv_management.entities.users.io.PreUsers;
import sai_adapa.projs.inv_management.services.UsersService;
import sai_adapa.projs.inv_management.entities.users.io.PreVendor;
import sai_adapa.projs.inv_management.services.VendorService;

@RestController
public class AdminController {

    AdminService adminService;
    VendorService vendorService;
    UsersService usersService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/admin/new"})

    public void signUp(@RequestBody PreAdmin preAdmin) {
        adminService.addUser(preAdmin.getEmail(), preAdmin.getPasswd());
    }


    @RequestMapping(method = RequestMethod.POST, value = {"/admin/login"})
    public String signIn(@RequestBody PreAdmin preAdmin) {
        if (adminService.verifyUser(preAdmin.getEmail(), preAdmin.getPasswd())) {
            return adminService.createSession(preAdmin.getEmail());
        } else {
            return "failed";
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/admin"})
    public void editAdminDetails(@RequestBody PreAdmin preAdmin) {
        adminService.editUser(adminService.getUser(preAdmin.getEmail()), preAdmin.getEmail(), preAdmin.getPasswd());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/admin"})
    public void deleteAdmin(@RequestBody PreAdmin preAdmin) {
        adminService.deleteUser(adminService.getUser(preAdmin.getEmail()));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/admin/logout"})
    public void signOut(@RequestBody PreAdmin preAdmin) {
        adminService.endSession(preAdmin.getEmail());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/admin/delete/vendor"})
    public void deleteVendor(@RequestBody PreVendor preVendor) {
        vendorService.deleteUser(vendorService.getUser(preVendor.getEmail()));
    }

    //available only to admin
    @RequestMapping(method = RequestMethod.DELETE, value = {"/admin/delete/user"})
    public void deleteUser(@RequestBody PreUsers preUsers) {
        usersService.deleteUser(usersService.getUser(preUsers.getEmail()));
    }

}