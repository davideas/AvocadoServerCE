package eu.davidea.avocadoserver.business.user;

import java.util.Date;

public class JwtToken {

    private String jti;
    private String token;
    private Date expiresAt;


    public JwtToken(String jti, String token, Date expiresAt) {
        this.jti = jti;
        this.token = token;
        this.expiresAt = expiresAt;
    }

}