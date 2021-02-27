package sai_adapa.projs.inv_management.auth.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UsersVerifierInterceptor implements HandlerInterceptor {

    UsersService usersService;

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override //Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (usersService.verifySession(request.getHeader("session_token"))) {
            return true;
        }
        try {
            response.getWriter().write("You are not an normal user");
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(401);
        return false;
    }
}
