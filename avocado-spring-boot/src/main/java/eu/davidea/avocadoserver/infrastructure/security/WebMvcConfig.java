package eu.davidea.avocadoserver.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication
public class WebMvcConfig implements WebMvcConfigurer {

    private JwtInterceptor jwtInterceptor;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public WebMvcConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns(
                        "/api/v1/**"
                ).
                excludePathPatterns(
                        "/api/v1/auth/login",
                        "/api/v1/auth/signup"
                );
    }

}