package eu.davidea.avocadoserver.infrastructure.crypto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StopWatch;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Not extending from SpringBootTest so the tests are faster.
 *
 * @author Davide
 * @since 12/08/2017
 */
public class CryptoServiceTest {

    @Test
    @DisplayName("BCrypt password matches")
    public void checkPasswordTest_OK() throws Exception {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(10, SecureRandom.getInstanceStrong());
        String password = "ThisIsSuperLong69CharsPasswordWithSome[*#ò+_@£$&/()StrangeCharacters!";

        StopWatch watch = new StopWatch("checkPasswordTest_OK");
        watch.start("hashPassword");
        String hashedPwd = bCrypt.encode(password);
        watch.stop();
        //System.out.println(hashedPwd);

        watch.start("checkPassword");
        assertTrue(bCrypt.matches(password, hashedPwd));
        watch.stop();
        System.out.println(watch.prettyPrint());
    }

    @Test
    @DisplayName("BCrypt password wrong")
    public void checkPasswordTest_Wrong() throws Exception {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(10, SecureRandom.getInstanceStrong());
        String password1 = "password1";
        String password2 = "password2";

        StopWatch watch = new StopWatch("checkPasswordTest_Wrong");
        watch.start("hashPassword");
        String hashedPwd1 = bCrypt.encode(password1);
        watch.stop();
        //System.out.println(hashedPwd1);

        watch.start("checkPassword");
        assertFalse(bCrypt.matches(password2, hashedPwd1));
        watch.stop();
        System.out.println(watch.prettyPrint());
    }

}