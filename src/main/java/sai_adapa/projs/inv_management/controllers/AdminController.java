package sai_adapa.projs.inv_management.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.exceptions.UserNotFoundException;
import sai_adapa.projs.inv_management.model.io.PreAdmin;
import sai_adapa.projs.inv_management.model.io.PreUsers;
import sai_adapa.projs.inv_management.model.io.PreVendor;
import sai_adapa.projs.inv_management.model.users.Admin;
import sai_adapa.projs.inv_management.services.AdminService;
import sai_adapa.projs.inv_management.services.UsersService;
import sai_adapa.projs.inv_management.services.VendorService;
import sai_adapa.projs.inv_management.tools.ResponseHandler;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AdminController {
    SessionIdentity sessionIdentity;
    AdminService adminService;
    VendorService vendorService;
    UsersService usersService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Autowired
    public void setSessionIdentity(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @Autowired
    public void setEmailVerifier(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
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
    public void signUp(@RequestBody PreAdmin preAdmin, HttpServletResponse response) {
        try {
            adminService.addUser(preAdmin.getEmail(), preAdmin.getPasswd());
        } catch (DataIntegrityViolationException e) {
            ResponseHandler.userAlreadyExists(response);
            return;
        } catch (NullPointerException e) {
            ResponseHandler.insufficientDetailsInRequest(response);
        }
        ResponseHandler.successfulCreate(response);
    }

    @RequestMapping(value = {"/vendor"})
    public Admin displayAdmin() {
        return adminService.displayUser(sessionIdentity.getEmail());
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/admin/login"})
    public String signIn(@RequestBody PreAdmin preAdmin, HttpServletResponse response) {
        try {
            if (adminService.verifyUser(preAdmin.getEmail(), preAdmin.getPasswd())) {
                return adminService.createSession(preAdmin.getEmail());
            } else {
                ResponseHandler.userVerificationFailed(response);
            }
        } catch (NullPointerException e) {
            ResponseHandler.userDoesNotExist(response);
        }
        return "k";
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/admin"})
    public void editAdminDetails(@RequestBody PreAdmin preAdmin, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preAdmin.getEmail(), response))
            return;
        try {
            adminService.editUser(adminService.getUser(preAdmin.getEmail()), preAdmin.getChanged_email(), preAdmin.getPasswd());
        } catch (NullPointerException e) {
            ResponseHandler.userDoesNotExist(response);
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/admin"})
    public void deleteAdmin(@RequestBody PreAdmin preAdmin, HttpServletResponse response) {
        try {
            adminService.deleteUser(adminService.getUser(preAdmin.getEmail()));
        } catch (NullPointerException e) {
            ResponseHandler.userDoesNotExist(response);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/admin/logout"})
    public void signOut(@RequestBody PreAdmin preAdmin, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preAdmin.getEmail(), response))
            return;

        try {
            adminService.endSession(preAdmin.getEmail());

        } catch (NullPointerException e) {
            ResponseHandler.userDoesNotExist(response);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/admin/delete/vendor"})
    public void deleteVendor(@RequestBody PreVendor preVendor, HttpServletResponse response) {
        try {
            vendorService.deleteUser(vendorService.getUser(preVendor.getEmail()));
        } catch (NullPointerException | UserNotFoundException e) {
            ResponseHandler.userDoesNotExist(response);
        }

    }

    //available only to admin
    @RequestMapping(method = RequestMethod.DELETE, value = {"/admin/delete/user"})
    public void deleteUser(@RequestBody PreUsers preUsers, HttpServletResponse response) {
        try {
            usersService.deleteUser(usersService.getUser(preUsers.getEmail()));
        } catch (UserNotFoundException e) {
            ResponseHandler.userDoesNotExist(response);
        }
    }

}
