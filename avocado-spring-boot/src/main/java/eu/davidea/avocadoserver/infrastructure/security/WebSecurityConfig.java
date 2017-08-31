package eu.davidea.avocadoserver.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.NoSuchAlgorithmException;

import eu.davidea.avocadoserver.business.user.LoginUseCase;

@Configuration
@EnableWebSecurity
@ConditionalOnWebApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private LoginUseCase loginUseCase;
    private PasswordEncoder bCryptEncoder;
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public WebSecurityConfig(LoginUseCase loginUseCase,
                             JwtAuthenticationEntryPoint unauthorizedHandler) throws NoSuchAlgorithmException {
        this.loginUseCase = loginUseCase;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.loginUseCase)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        return new BCryptPasswordEncoder(10, java.security.SecureRandom.getInstanceStrong());
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Handle unauthorized access
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // Don't create session
                .sessionManagement().disable()
                // Enable CORS
                .cors().and()
                // Disable CSRF
                .csrf().disable()
                // Force HTTPS for all requests
                .requiresChannel().anyRequest().requiresSecure().and()
                .authorizeRequests()
                // Allow to login with no token
                .antMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/auth/signup").permitAll()
                .antMatchers("/api/**").authenticated();
                // All others URLs must be authenticated
                //.anyRequest().authenticated();

        httpSecurity.
                addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

}