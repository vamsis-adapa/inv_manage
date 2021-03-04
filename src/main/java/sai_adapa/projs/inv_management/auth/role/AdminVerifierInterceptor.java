package sai_adapa.projs.inv_management.auth.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.model.users.Admin;
import sai_adapa.projs.inv_management.services.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AdminVerifierInterceptor implements HandlerInterceptor {

    AdminService adminService;
    SessionIdentity sessionIdentity;

    @Autowired
    public AdminVerifierInterceptor(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override //Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String email = adminService.getUserEmailFromSession(request.getHeader("session_token"));
        if (email!= null) {
            sessionIdentity.setIdentity(email);
            System.out.println("verified");
            return true;
        }
        try {
            response.getWriter().write("You are not an admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("not ver");
        response.setStatus(401);
        return false;
    }
}
