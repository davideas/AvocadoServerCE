package eu.davidea.avocadoserver;

import eu.davidea.avocadoserver.infrastructure.filters.RequestLoggingFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Main entry point for launching the SpringBoot application.
 */
@Configuration
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = JmxAutoConfiguration.class)
@MapperScan("eu.davidea.avocadoserver.persistence.mybatis.mappers")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    @ConditionalOnWebApplication
    public FilterRegistrationBean logRequestsFilter() {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestLoggingFilter());
        bean.addInitParameter(RequestLoggingFilter.ENABLED, "true");
        bean.addInitParameter(RequestLoggingFilter.LOG_HEADER_PREFIXES, "/api/*");
        bean.addInitParameter(RequestLoggingFilter.INCLUDE_QUERY_STRING, "true");
        bean.addInitParameter(RequestLoggingFilter.INCLUDE_CLIENT_INFO, "true");
        bean.addInitParameter(RequestLoggingFilter.INCLUDE_PAYLOAD, "true");
        bean.addInitParameter(RequestLoggingFilter.INCLUDE_HEADERS, "true");
        bean.setOrder(0);
        return bean;
    }

    @Bean
    @ConditionalOnWebApplication
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false); // you USUALLY want this
        config.addAllowedOrigin("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        //config.setMaxAge(3600L); // cache pre-flight response for 1h

        config.addAllowedHeader("*");

        // REST /api is used by the mobile clients and /api/admin is used by admin web UI
        source.registerCorsConfiguration("/api/**", config);

        // enable Actuator endpoint to be used by mobile client for checking if server is app
        //source.registerCorsConfiguration("/health", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(1);

        return bean;
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}