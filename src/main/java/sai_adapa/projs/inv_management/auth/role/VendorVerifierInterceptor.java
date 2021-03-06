package sai_adapa.projs.inv_management.auth.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.exceptions.UserNotFoundException;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.services.VendorService;
import sai_adapa.projs.inv_management.tools.ResponseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class VendorVerifierInterceptor implements HandlerInterceptor {
    VendorService vendorService;
    SessionIdentity sessionIdentity;

    @Autowired
    public void setSessionIdentity(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @Override ///Todo: add error resp in case of failure
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Vendor vendor;
        try {
            vendor = vendorService.getUserBySession(request.getHeader("session_token"));
        } catch (UserNotFoundException e) {
            vendor = null;
        }
        if (vendor != null) {
            sessionIdentity.setIdentity(vendor.getEmail());
            return true;

        }
        ResponseHandler.userVerificationFailed(response);
        return false;
    }
}
