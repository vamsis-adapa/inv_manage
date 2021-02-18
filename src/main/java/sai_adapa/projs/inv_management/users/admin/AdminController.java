package sai_adapa.projs.inv_management.users.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AdminController {

    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(method = RequestMethod.POST,value = {"/admin"})
    public void signUp(@RequestBody PreAdmin preAdmin)
    {
        System.out.println("d;falkj");
        adminService.addUser(preAdmin.getEmail(),preAdmin.getPasswd());
    }


    @RequestMapping(method = RequestMethod.POST,value = {"/admin/login"})
    public String signIn(@RequestBody PreAdmin preAdmin)
    {
        if (adminService.verifyUser(preAdmin.getEmail(),preAdmin.getPasswd()))
        {
            return adminService.createSession(preAdmin.getEmail());
        }
        else {
            return "failed";
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,value = {"/admin/logout"})
    public void signOut(@RequestBody PreAdmin preAdmin)
    {
        adminService.endSession(preAdmin.getEmail());
    }

}
