package sai_adapa.projs.inv_management.auth;

import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.services.AdminService;
import sai_adapa.projs.inv_management.tools.SpringContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AdminVerifierInterceptor implements HandlerInterceptor {


    private AdminService getAdminService() {
        return SpringContext.getBean(AdminService.class);
    }


    @Override //Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (getAdminService().verifyToken(request.getHeader("session_token"))) {
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
