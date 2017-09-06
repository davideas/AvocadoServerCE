package eu.davidea.avocadoserver;

import eu.davidea.avocadoserver.business.enums.EnumAuthority;
import eu.davidea.avocadoserver.business.enums.EnumUserStatus;
import eu.davidea.avocadoserver.business.user.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Configuration
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ApplicationTests {

    @Bean
    public User admin() {
        User user = new User();
        user.setUsername("davideas");
        user.setPassword("avocado_ce");
        user.setAuthority(EnumAuthority.ROLE_ADMIN);
        user.setStatus(EnumUserStatus.ACTIVE);
        return user;
    }

    @Bean
    public Device webDevice() {
        return new Device() {
            @Override
            public boolean isNormal() {
                return true;
            }

            @Override
            public boolean isMobile() {
                return false;
            }

            @Override
            public boolean isTablet() {
                return false;
            }

            @Override
            public DevicePlatform getDevicePlatform() {
                return DevicePlatform.UNKNOWN;
            }
        };
    }

}