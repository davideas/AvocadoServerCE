/*
 * Copyright 2017 Davidea Solutions Sprl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.davidea.avocadoserver;

import eu.davidea.avocadoserver.infrastructure.filters.RequestLoggingFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.*;
import javax.servlet.annotation.ServletSecurity;

/**
 * Main entry point for launching the SpringBoot application.
 */
//@EnableAsync
@Configuration
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = {
        JmxAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
@MapperScan("eu.davidea.avocadoserver.persistence.mybatis.mappers")
public class Application extends SpringBootServletInitializer {

    private static final String DISPATCHER_SERVLET_NAME = "dispatcherServlet";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Override
    protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
        return super.createRootApplicationContext(servletContext);
    }

// Force HTTPS:
// HTTP redirects to HTTPS when using Spring Boot and External Tomcat.
// Use this code when no Spring Security is added and configured!
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        super.onStartup(container);

        // Get the existing dispatcher servlet
        ServletRegistration.Dynamic dispatcher = (ServletRegistration.Dynamic) container.getServletRegistration(DISPATCHER_SERVLET_NAME);

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
    public CorsFilter corsFilter() {
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

        return new CorsFilter(source);
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}