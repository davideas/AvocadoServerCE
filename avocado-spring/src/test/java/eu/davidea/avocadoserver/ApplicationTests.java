package eu.davidea.avocadoserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource(properties = "enigma = 42")
@ActiveProfiles("test")
public class ApplicationTests {

    @Value("${enigma}")
    Integer enigma;

    @Test
    @DisplayName("Context Loads!")
    void applicationContextInjectedIntoMethod(ApplicationContext applicationContext) {
        assertNotNull(applicationContext, "ApplicationContext should have been injected by Spring");
    }

    @Test
    @DisplayName("Properties via Annotation")
    void propertySource() {
        Assertions.assertEquals((int) enigma, 42);
    }

}