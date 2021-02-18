package sai_adapa.projs.inv_management.auth.user_authorization;

import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HttpServletBean;
import sai_adapa.projs.inv_management.tools.SpringContext;
import sai_adapa.projs.inv_management.users.vendor.VendorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VendorVerifier implements HandlerInterceptor {
    private VendorService getVendorService()
    {
        return SpringContext.getBean(VendorService.class);
    }

    @Override ///Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
      return getVendorService().verifySession(request.getHeader("session_token"));
    }
}
