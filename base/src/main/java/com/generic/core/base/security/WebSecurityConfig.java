package com.generic.core.base.security;

import com.generic.core.base.security.jwt.JwtEntryPoint;
import com.generic.core.base.security.jwt.JwtRequestFilter;
import com.generic.core.base.security.jwt.JwtUserDetailsService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().configurationSource(corsConfigurationSource())
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/user-profile/sign-in").permitAll()
                    .antMatchers("/api/user-profile/{uuid}/otp").permitAll()
                    .antMatchers("/api/info").permitAll()
                    .antMatchers("/api/subscriber/public/sign-up").permitAll()
                    .antMatchers("/public/api/callback/cashbox/pgi-transaction-update").permitAll()
                    .antMatchers("/api/client-subscriber/verify/{token}").permitAll()
                    // partners
                    .antMatchers("/public/api/callback/v1/cebuana/posted-payroll").permitAll()
                    .antMatchers("/public/api/callback/v1/cebuana/claimed-payroll").permitAll()
                    .antMatchers("/private/api/subscriber/disbursement/").permitAll()
            .antMatchers("/public/api/**").permitAll()
            .antMatchers("/api/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.csrf().disable();
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    /*@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        
    }
    
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }*/
    
}
