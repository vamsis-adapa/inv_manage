package sai_adapa.projs.inv_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sai_adapa.projs.inv_management.auth.user_authorization.AdminVerifier;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new AdminVerifier()).addPathPatterns("/admin/logout");
    }

}
