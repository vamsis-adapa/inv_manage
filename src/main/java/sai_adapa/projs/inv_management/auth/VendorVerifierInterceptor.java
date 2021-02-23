package sai_adapa.projs.inv_management.auth;

import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.services.VendorService;
import sai_adapa.projs.inv_management.tools.SpringContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VendorVerifierInterceptor implements HandlerInterceptor {
    private VendorService getVendorService() {
        return SpringContext.getBean(VendorService.class);
    }

    @Override ///Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return getVendorService().verifySession(request.getHeader("session_token"));
    }
}
