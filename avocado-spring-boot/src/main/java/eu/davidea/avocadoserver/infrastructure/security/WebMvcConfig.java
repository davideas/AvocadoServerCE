package eu.davidea.avocadoserver.infrastructure.security;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//@ConditionalOnWebApplication
public class WebMvcConfig implements WebMvcConfigurer {

//    private JwtInterceptor jwtInterceptor;
//
//    @SuppressWarnings("SpringJavaAutowiringInspection")
//    @Autowired
//    public WebMvcConfig(JwtInterceptor jwtInterceptor) {
//        this.jwtInterceptor = jwtInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns(
//                        "/api/v1/**"
//                ).
//                excludePathPatterns(
//                        "/api/v1/auth/login",
//                        "/api/v1/auth/signup"
//                );
//    }

}