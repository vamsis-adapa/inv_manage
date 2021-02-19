package sai_adapa.projs.inv_management.config;

import org.apache.catalina.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sai_adapa.projs.inv_management.auth.user_authorization.AdminVerifier;
import sai_adapa.projs.inv_management.auth.user_authorization.UsersVerifier;
import sai_adapa.projs.inv_management.auth.user_authorization.VendorVerifier;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {

        registry.addInterceptor(new AdminVerifier()).addPathPatterns("/admin","/admin/**").excludePathPatterns("/admin/new","/admin/login");
        registry.addInterceptor(new UsersVerifier()).addPathPatterns("/app_user/logout");
        registry.addInterceptor(new VendorVerifier()).addPathPatterns(("/vendor/logout"));
    }

}
