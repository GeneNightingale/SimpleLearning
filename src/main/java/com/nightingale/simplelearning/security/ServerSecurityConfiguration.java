package com.nightingale.simplelearning.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    public ServerSecurityConfiguration(UserDetailsService userDetailsService, JwtTokenFilter jwtTokenFilter) {
        super();
        this.userDetailsService = userDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http
                .cors()
                .and()
                .csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                ex.getMessage()
                        )
                )
                .and();

        http.authorizeRequests()
                // public endpoints
                .antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/api/register").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/hellouser").authenticated()
                .antMatchers(HttpMethod.GET, "/api/hellostudent").hasRole("STUDENT")
                .antMatchers(HttpMethod.GET, "/api/helloteacher").hasRole("TEACHER")
                .antMatchers(HttpMethod.GET, "/api/helloadmin").hasRole("ADMIN")
                //.antMatchers(HttpMethod.GET, "api/material/**").hasRole("TEACHER")
                //.antMatchers(HttpMethod.POST, "api/material/**").hasRole("TEACHER")
                //.antMatchers(HttpMethod.PUT, "api/material/**").hasRole("TEACHER")
                //.antMatchers(HttpMethod.DELETE, "api/material/**").hasRole("TEACHER")
                //.antMatchers(HttpMethod.POST, "api/page/**").hasRole("TEACHER")
                //.antMatchers(HttpMethod.PUT, "api/page/**").hasRole("TEACHER")
                //.antMatchers(HttpMethod.DELETE, "api/page/**").hasRole("TEACHER")
                //.antMatchers("/api/**").authenticated();
                               .antMatchers("/**").permitAll();


        http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }
}

