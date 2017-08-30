package eu.davidea.avocadoserver.infrastructure.security;

import eu.davidea.avocadoserver.business.user.LoginUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@ConditionalOnWebApplication
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private LoginUseCase loginUseCase;
    private PasswordEncoder bcryptEncoder;
    private JwtAuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfig(LoginUseCase loginUseCase, JwtAuthenticationProvider authenticationProvider)
            throws NoSuchAlgorithmException {
        this.loginUseCase = loginUseCase;
        this.authenticationProvider = authenticationProvider;
        this.bcryptEncoder = new BCryptPasswordEncoder(10, java.security.SecureRandom.getInstanceStrong());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.loginUseCase)
                .passwordEncoder(this.bcryptEncoder);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Enable CORS
                .cors().and()
                // Disable CSRF
                .csrf().disable()
                // Force HTTPS for all requests
                .requiresChannel().anyRequest().requiresSecure().and()
                .authorizeRequests()
                // Allow to login with no token
                .antMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/auth/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/auth/logout").authenticated()
                // All others URLs must be authenticated
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

}