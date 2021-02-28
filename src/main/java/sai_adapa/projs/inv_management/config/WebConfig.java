package sai_adapa.projs.inv_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sai_adapa.projs.inv_management.auth.role.AdminVerifierInterceptor;
import sai_adapa.projs.inv_management.auth.role.UsersVerifierInterceptor;
import sai_adapa.projs.inv_management.auth.role.VendorVerifierInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    AdminVerifierInterceptor adminVerifierInterceptor;
    UsersVerifierInterceptor usersVerifierInterceptor;
    VendorVerifierInterceptor vendorVerifierInterceptor;

    @Autowired
    public void setAdminVerifierInterceptor(AdminVerifierInterceptor adminVerifierInterceptor) {
        this.adminVerifierInterceptor = adminVerifierInterceptor;
    }

    @Autowired

    public void setUsersVerifierInterceptor(UsersVerifierInterceptor usersVerifierInterceptor) {
        this.usersVerifierInterceptor = usersVerifierInterceptor;
    }

    @Autowired
    public void setVendorVerifierInterceptor(VendorVerifierInterceptor vendorVerifierInterceptor) {
        this.vendorVerifierInterceptor = vendorVerifierInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminVerifierInterceptor).addPathPatterns("/admin", "/admin/**").excludePathPatterns("/admin/new", "/admin/login");
        registry.addInterceptor(usersVerifierInterceptor).addPathPatterns("/app_user/**", "/app_user").excludePathPatterns("/app_user/new", "/app_user/login","/order");
        registry.addInterceptor(vendorVerifierInterceptor).addPathPatterns("/vendor/**", "/vendor").excludePathPatterns("/vendor/new", "/vendor/login");
    }
}
