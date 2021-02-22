package sai_adapa.projs.inv_management.auth.user_authorization;

import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.tools.SpringContext;
import sai_adapa.projs.inv_management.users.user.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersVerifier implements HandlerInterceptor {

    private UsersService getUsersService() {
        return SpringContext.getBean(UsersService.class);
    }

    @Override //Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        return getUsersService().verifySession(request.getHeader("session_token"));
    }
}
