package com.prgrms.devcourse.configures;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure {

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/assets/**");// 여기에 적용하면 spring security filter chain을 태우지 않겠다
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .passwordManagement(withDefaults())
            .authorizeHttpRequests((auth) -> auth.requestMatchers("/me")
                    .hasAnyRole("USER", "ADMIN")
                    .anyRequest().permitAll())
            .formLogin(withDefaults())
            .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/"))
            .rememberMe((remember) -> remember.rememberMeParameter("remember-me")
                    .tokenValiditySeconds(300));

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {

    UserBuilder users = User.withDefaultPasswordEncoder();
    UserDetails admin = users.username("admin")
            .password("admin123")
            .roles("ADMIN")
            .build();
    UserDetails user = users.username("user")
            .password("user123")
            .roles("USER")
            .build();
    return new InMemoryUserDetailsManager(admin, user);
  }
 }