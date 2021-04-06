package sai_adapa.projs.inv_management.auth.role;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.exceptions.UserNotFoundException;
import sai_adapa.projs.inv_management.services.UsersService;
import sai_adapa.projs.inv_management.tools.ResponseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UsersVerifierInterceptor implements HandlerInterceptor {

    UsersService usersService;
    SessionIdentity identity;

    @Autowired
    public void setIdentity(SessionIdentity identity) {
        this.identity = identity;
    }

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,  HttpServletResponse response,  Object handler) {
        try {
            if (usersService.verifySession(request.getHeader("session_token"))) {
                identity.setIdentity(usersService.getUsersBySession(request.getHeader("session_token")).getEmail());
                return true;
            }
        } catch (UserNotFoundException e) {
            ResponseHandler.userDoesNotExist(response);
            return false;
        }
        ResponseHandler.userVerificationFailed(response);
        return false;
    }
}
