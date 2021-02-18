package sai_adapa.projs.inv_management.users.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/admin"})
//    @ResponseStatus(value = H)
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

    @RequestMapping(method = RequestMethod.DELETE, value = {"/admin/logout"})
    public void signOut(@RequestBody PreAdmin preAdmin) {
        adminService.endSession(preAdmin.getEmail());
    }

}
