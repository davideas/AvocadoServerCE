package eu.davidea.avocadoserver.boundary.rest.api.auth;

/**
 * @author Davide
 * @since 27/08/2017
 */
public class AuthenticationRequest {

    private String username;
    private CharSequence password;

    public AuthenticationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CharSequence getPassword() {
        return password;
    }

    public void setPassword(CharSequence password) {
        this.password = password;
    }

}