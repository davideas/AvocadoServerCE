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

/**
 * Main entry point for launching the SpringBoot application.
 */
@Configuration
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

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}