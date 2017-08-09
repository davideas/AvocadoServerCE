package eu.davidea.avocadoserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Print information about the version of the application when the application is started.
 */
@Component
public class PrintAppInfo {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${info.build.name}")
    private String buildName;

    @Value("${info.build.description}")
    private String buildDescription;

    @Value("${info.build.version}")
    private String buildVersion;

    @Value("${info.build.builtBy}")
    private String builtBy;

    @PostConstruct
    public void printInfo() {
        logger.info("\nAppInfo:\n\tname: {},\n\tdescription: {},\n\tversion: {},\n\tbuilt by: {}\n",
                buildName, buildDescription, buildVersion, builtBy);
    }
}
