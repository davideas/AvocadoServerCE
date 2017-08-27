package eu.davidea.avocadoserver.infrastructure.utilities;

import javax.validation.constraints.NotNull;

public class EmailValidator {

    private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    /**
     * Validates email address against email regular expression.
     *
     * @param email an email address to check
     * @return true if email address is valid otherwise return false.
     */
    public static boolean isValidEmailAddress(@NotNull String email) {
        return email.matches(EMAIL_REGEX);
    }

}