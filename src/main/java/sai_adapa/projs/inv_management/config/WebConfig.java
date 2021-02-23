package sai_adapa.projs.inv_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sai_adapa.projs.inv_management.auth.AdminVerifierInterceptor;
import sai_adapa.projs.inv_management.auth.UsersVerifierInterceptor;
import sai_adapa.projs.inv_management.auth.VendorVerifierInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {

        registry.addInterceptor(new AdminVerifierInterceptor()).addPathPatterns("/admin","/admin/**").excludePathPatterns("/admin/new","/admin/login");
        registry.addInterceptor(new UsersVerifierInterceptor()).addPathPatterns("/app_user/logout");
        registry.addInterceptor(new VendorVerifierInterceptor()).addPathPatterns(("/vendor/logout"));
    }

}
