package eu.davidea.avocadoserver.infrastructure.security;

import eu.davidea.avocadoserver.ApplicationTests;
import eu.davidea.avocadoserver.TestConfig;
import eu.davidea.avocadoserver.business.enums.EnumAuthority;
import eu.davidea.avocadoserver.business.enums.EnumUserStatus;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.ExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

//@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource(properties = {
        "jwt.secret = 'myTestSecret'",
        "jwt.duration = 1",
})
class JwtTokenServiceTest extends ApplicationTests {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.duration}")
    private long jwtDuration;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private Device webDevice;

    private User user;

    @BeforeEach
    public void setup() {
        this.user = createMockUser();
    }

    @Test
    @DisplayName("Generate new JWT Token")
    void generateToken() {
        JwtToken jwtToken = jwtTokenService.generateToken(user, webDevice);
        assertNotNull(jwtToken, "jwtToken is null");
        assertNotNull(jwtToken.getJti(), "jti is null");
        assertNotNull(jwtToken.getToken(), "token is null");
        assertNotNull(jwtToken.getSubject(), "subject is null");
        assertNotNull(jwtToken.getAudience(), "audience is null");
        assertNotNull(jwtToken.getIssuedAt(), "issuedAt is null");
        assertNotNull(jwtToken.getExpiresAt(), "expiresAt is null");
    }

    @Test
    @DisplayName("Validate good JWT Token")
    void validateToken_OK() {
        JwtToken jwtToken = jwtTokenService.generateToken(user, webDevice);
        JwtToken resultToken = jwtTokenService.validateToken(jwtToken.getToken());
        resultToken.setToken(jwtToken.getToken()); // Otherwise the next assert fails
        assertThat(resultToken, samePropertyValuesAs(jwtToken));
    }

    @Test
    @DisplayName("Validate expired JWT Token")
    void validateToken_Expired() throws InterruptedException {
        JwtTokenService jwtTokenService = new JwtTokenService(jwtSecret, 0); // 0 min, already expired
        jwtTokenService.init();
        JwtToken jwtToken = jwtTokenService.generateToken(user, webDevice);
        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> jwtTokenService.validateToken(jwtToken.getToken()));
        assertEquals(ExceptionCode.TOKEN_EXPIRED.toString(), exception.getCode());
    }

    private User createMockUser() {
        User user = new User();
        user.setUsername("davideas");
        user.setPassword("avocado_ce");
        user.setAuthority(EnumAuthority.ROLE_ADMIN);
        user.setStatus(EnumUserStatus.ACTIVE);
        return user;
    }

}