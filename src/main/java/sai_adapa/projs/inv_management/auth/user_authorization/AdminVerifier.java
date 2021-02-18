package sai_adapa.projs.inv_management.auth.user_authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;
import sai_adapa.projs.inv_management.users.admin.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configurable
public class AdminVerifier implements HandlerInterceptor {

    AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {

        System.out.println(request.getParameter("email"));
        return  adminService.verifyToken(request.getHeader("session_token"));
    }

}
