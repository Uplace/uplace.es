package com.arnaugarcia.uplace.config;

import com.arnaugarcia.uplace.security.*;
import com.arnaugarcia.uplace.security.jwt.*;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.annotation.PostConstruct;

@Configuration
@Import(SecurityProblemSupport.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService,TokenProvider tokenProvider,CorsFilter corsFilter, SecurityProblemSupport problemSupport) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
        .and()
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()

            // FLATS
            .antMatchers(HttpMethod.GET, "/api/flats/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/flats").permitAll()
            .antMatchers(HttpMethod.POST, "/api/flats").authenticated()
            .antMatchers(HttpMethod.PUT, "/api/flats/**").authenticated()
            .antMatchers(HttpMethod.DELETE, "/api/flats/**").authenticated()
            .antMatchers(HttpMethod.GET, "/api/markers").permitAll()


            // HOUSES
            /*.antMatchers(HttpMethod.GET, "/api/houses/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/houses").permitAll()
            .antMatchers(HttpMethod.POST, "/api/houses").authenticated()*/

            // RURALS
            /*.antMatchers(HttpMethod.GET, "/api/rurals/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/rurals").permitAll()
            .antMatchers(HttpMethod.POST, "/api/rurals").authenticated()*/

            // TOWERS
            /*.antMatchers(HttpMethod.GET, "/api/towers/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/towers").permitAll()
            .antMatchers(HttpMethod.POST, "/api/towers").authenticated()*/

            // LOFTS
            /*.antMatchers(HttpMethod.GET, "/api/lofts/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/lofts").permitAll()
            .antMatchers(HttpMethod.POST, "/api/lofts").authenticated()*/

            .antMatchers(HttpMethod.GET, "/api/properties").permitAll()
            .antMatchers(HttpMethod.GET, "/api/agents").permitAll()
            .antMatchers(HttpMethod.POST, "/api/agents").authenticated()
            .antMatchers("/api/notifications/**").authenticated()
            .antMatchers("/api/notifications").authenticated()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/profile-info").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/v2/api-docs/**").permitAll()
            .antMatchers("/swagger-resources/configuration/ui").permitAll()
            .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
        .and()
            .apply(securityConfigurerAdapter());

    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

}
