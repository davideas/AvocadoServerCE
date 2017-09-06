package eu.davidea.avocadoserver.infrastructure.security;

import eu.davidea.avocadoserver.ApplicationTests;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = {
        "jwt.secret = 'myTestSecret'",
        "jwt.duration = 1",
})
class JwtTokenServiceTest extends ApplicationTests {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private User admin;

    @Autowired
    private Device webDevice;

    @Test
    @DisplayName("Generate new JWT Token")
    void generateToken() {
        JwtToken jwtToken = jwtTokenService.generateToken(admin, webDevice);
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
        JwtToken jwtToken = jwtTokenService.generateToken(admin, webDevice);
        JwtToken resultToken = jwtTokenService.validateToken(jwtToken.getToken());
        resultToken.setToken(jwtToken.getToken()); // Otherwise the next assert fails
        assertThat(resultToken, samePropertyValuesAs(jwtToken));
    }

    @Test
    @DisplayName("Validate expired JWT Token")
    void validateToken_Expired() throws InterruptedException {
        JwtTokenService jwtTokenService = new JwtTokenService("myTestSecret", 0); // 0 min, already expired
        jwtTokenService.init();
        JwtToken jwtToken = jwtTokenService.generateToken(admin, webDevice);
        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> jwtTokenService.validateToken(jwtToken.getToken()));
        assertEquals(ExceptionCode.TOKEN_EXPIRED.toString(), exception.getCode());
    }

}