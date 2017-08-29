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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.annotation.ServletSecurity;

/**
 * Main entry point for launching the SpringBoot application.
 */
@Configuration
@EnableAsync
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = JmxAutoConfiguration.class)
@MapperScan("eu.davidea.avocadoserver.persistence.mybatis.mappers")
public class Application extends SpringBootServletInitializer {

    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        super.onStartup(container);

        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(Application.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);

        // Force HTTPS, and don't specify any roles for this constraint
        HttpConstraintElement forceHttpsConstraint = new HttpConstraintElement(ServletSecurity.TransportGuarantee.CONFIDENTIAL);
        ServletSecurityElement securityElement = new ServletSecurityElement(forceHttpsConstraint);

        // Add the security element to the servlet
        dispatcher.setServletSecurity(securityElement);
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

        // REST '/api' is used by the mobile clients and /api/admin is used by admin web UI
        source.registerCorsConfiguration("/api/**", config);

        // Enable Actuator endpoint to be used by mobile client for checking if server is app
        //source.registerCorsConfiguration("/health", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(1);

        return bean;
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}