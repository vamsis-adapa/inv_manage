package sai_adapa.projs.inv_management.users.user;

import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/app_user"})
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

    @RequestMapping(method = RequestMethod.PATCH, value = {"/app_user"})
    public void editUser(@RequestBody PreUsers preUsers, @RequestHeader("session_token") String key) {
        usersService.editUser(usersService.getUsersBySession(key), preUsers.getName(), preUsers.getEmail(), preUsers.getDetails(), preUsers.getPasswd());
    }


    @RequestMapping(method = RequestMethod.DELETE, value = {"/app_user/logout"})
    public void signOut(@RequestBody PreUsers preUsers) {
        usersService.endSession(usersService.getUser(preUsers.getEmail()));
    }

}
