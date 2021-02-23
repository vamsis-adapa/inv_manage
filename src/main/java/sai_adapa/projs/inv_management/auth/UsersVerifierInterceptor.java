package sai_adapa.projs.inv_management.auth;

import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.services.UsersService;
import sai_adapa.projs.inv_management.tools.SpringContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersVerifierInterceptor implements HandlerInterceptor {

    private UsersService getUsersService() {
        return SpringContext.getBean(UsersService.class);
    }

    @Override //Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return getUsersService().verifySession(request.getHeader("session_token"));
    }
}
