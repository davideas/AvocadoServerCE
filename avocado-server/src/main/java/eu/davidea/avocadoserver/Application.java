package eu.davidea.avocadoserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(exclude = JmxAutoConfiguration.class)
@MapperScan("eu.davidea.avocadoserver.persistence.mybatis.mappers")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

//    @Bean(destroyMethod="")
//    public DataSource dataSource() throws Exception {
//        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
//        return dataSourceLookup.getDataSource("java:comp/env/jdbc/avocadoDB");
//    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}