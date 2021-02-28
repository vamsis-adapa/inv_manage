package sai_adapa.projs.inv_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.model.orders.io.DisplayableOrder;
import sai_adapa.projs.inv_management.model.users.Users;
import sai_adapa.projs.inv_management.model.users.io.PreUsers;
import sai_adapa.projs.inv_management.services.UsersService;

import java.util.List;

@RestController
public class UsersController {

    SessionIdentity sessionIdentity;
    UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    public void setEmailVerifier(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @Autowired
    public void setSessionIdentity(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @RequestMapping(value = {"app_user"})
    public Users getUserDetails() {
        return usersService.getUser(sessionIdentity.getEmail());
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/app_user/new"})
    public void signUp(@RequestBody PreUsers preUser) {
        usersService.addUser(preUser.getName(), preUser.getEmail(), preUser.getDetails(), preUser.getPasswd());
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/app_user/login"})
    public String signIn(@RequestBody PreUsers preUsers) {
        if (usersService.verifyUser(preUsers.getEmail(), preUsers.getPasswd())) {
            return usersService.createSession(preUsers.getEmail());
        } else
            return "failed";
    }

    @RequestMapping(value = {"/app_user/me"})
    public Users displayVendor() {
        return usersService.displayUser(sessionIdentity.getEmail());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/app_user"})
    public void editUser(@RequestBody PreUsers preUsers) {
        if (!sessionIdentity.verifyIdentity(preUsers.getEmail())) {
            //throw
            return;
        }
        usersService.editUser(usersService.getUser(preUsers.getEmail()), preUsers.getName(), preUsers.getChanged_email(), preUsers.getDetails(), preUsers.getPasswd());
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/app_user/orders"})
    public List<DisplayableOrder> getOrderReport(@RequestBody PreUsers preUsers)
    {
        if (!sessionIdentity.verifyIdentity(preUsers.getEmail()))
        {
            //throw
            return null;
        }
        return usersService.getUserOrderReport(preUsers.getEmail());
    }


    @RequestMapping(method = RequestMethod.DELETE, value = {"/app_user/logout"})
    public void signOut(@RequestBody PreUsers preUsers) {
        if (!sessionIdentity.verifyIdentity(preUsers.getEmail())) {
            //throw
            return;
        }
        usersService.endSession(usersService.getUser(preUsers.getEmail()));
    }

}
