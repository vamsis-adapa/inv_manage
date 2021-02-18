package sai_adapa.projs.inv_management.auth.user_authorization;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.tools.SpringContext;
import sai_adapa.projs.inv_management.users.admin.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configurable
public class AdminVerifier implements HandlerInterceptor {


    private AdminService getAdminService() {
        return SpringContext.getBean(AdminService.class);
    }


    @Override //Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return getAdminService().verifyToken(request.getHeader("session_token"));
    }

}
