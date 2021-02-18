package sai_adapa.projs.inv_management.users.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/app_user"})
    public void signUp(@RequestBody PreUsers preUser) {
        usersService.addUser(preUser.getName(),preUser.getE_mail(),preUser.getDetails(),preUser.getPasswd());
    }

    @RequestMapping(method = RequestMethod.POST,value = {"/app_user/login"})
    public String signIn(@RequestBody PreUsers preUsers)
    {
        if ( usersService.verifyUser(preUsers.getE_mail(),preUsers.getPasswd()))
        {
            return usersService.createSession(preUsers.getE_mail());
        }
        else
            return "failed";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/app_user/logout"})
    public void signOut(@RequestBody PreUsers preUsers)
    {
        usersService.endSession(usersService.getUser(preUsers.getE_mail()));
    }

}
