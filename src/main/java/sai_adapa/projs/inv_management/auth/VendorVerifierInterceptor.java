package sai_adapa.projs.inv_management.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.services.VendorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class VendorVerifierInterceptor implements HandlerInterceptor {
    VendorService vendorService;


    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @Override ///Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (vendorService.verifySession(request.getHeader("session_token")))
            return true;
        try {
            response.getWriter().write("You are not a vendor");
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(401);
        return false;
    }
}
