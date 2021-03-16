package sai_adapa.projs.inv_management.auth.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.services.AdminService;
import sai_adapa.projs.inv_management.tools.ResponseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String email = adminService.getUserEmailFromSession(request.getHeader("session_token"));
        if (email != null) {
            sessionIdentity.setIdentity(email);
            System.out.println("verified");
            return true;
        }
        ResponseHandler.userVerificationFailed(response);
        return false;
    }
}
