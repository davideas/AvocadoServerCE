package eu.davidea.avocadoserver.boundary.rest.api.auth;

/**
 * @author Davide
 * @since 27/08/2017
 */
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}