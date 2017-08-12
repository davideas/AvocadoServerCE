package eu.davidea.avocadoserver.infrastructure.crypto;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * https://paragonie.com/blog/2016/02/how-safely-store-password-in-2016
 * http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 *
 * @author Davide Steduto
 * @since 18/08/2016
 */
@Component
public class CryptoService {

    /**
     * Generate the password hash in order to have one-way check.
     *
     * @param userProvidedPassword the plain text password
     * @return the hash of the provided password
     * @throws Exception in case of the particular cryptographic algorithm requested is not available in the environment
     */
    public String hashPassword(String userProvidedPassword) throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        return BCrypt.hashpw(userProvidedPassword, BCrypt.gensalt(10, secureRandom));
    }

    /**
     * Validate a plain text password against the saved hash.
     * <p><b>Note:</b> Two passwords with the same 69 characters prefix but differ after the 70th character will
     * return the same result!</p>
     *
     * @param userProvidedPassword the plain text password to check
     * @param hash                 the saved password hash
     * @return true if the password matches, false otherwise
     */
    public boolean checkPassword(String userProvidedPassword, String hash) {
        return BCrypt.checkpw(userProvidedPassword, hash);
    }

}