package com.yyqian.imagine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.http.HttpMethod.GET;

/**
 * Created by yyqian on 12/4/15.
 * Config Web security
 * To switch off the default web security configuration completely you can add a bean with @EnableWebSecurity
 * (this does not disable the authentication manager configuration)
 */
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * The name of the configureGlobal method is not important.
   * However, it is important to only configure AuthenticationManagerBuilder in a class annotated
   * with either @EnableWebSecurity, @EnableGlobalMethodSecurity, or @EnableGlobalAuthentication.
   * Doing otherwise has unpredictable results.
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Configuration
  public static class DefaultWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
          .authorizeRequests()
              .antMatchers("/admin","/admin/**").hasAuthority("ADMIN")
              .antMatchers(GET, "/post/**/editor", "/user/self").authenticated()
              .antMatchers("/forgot", "/user", "/login").permitAll()
              .antMatchers(GET, "/js/**", "/css/**", "/images/**", "**/favicon.ico").permitAll()
              .antMatchers(GET, "/resetpw/**", "/post", "/post/**", "/comment", "/comment/**", "/user/**", "/", "/about", "/logout").permitAll()
              .antMatchers("/h2-console/**").permitAll()
              .anyRequest().authenticated()
              .and()
          .formLogin()
              .loginPage("/login")
              .failureUrl("/login?error")
              .usernameParameter("username")
              .permitAll()
              .and()
          .logout()
              .logoutSuccessUrl("/").permitAll();
      httpSecurity.csrf().disable(); // TODO: Enable it in production
      //httpSecurity.headers().frameOptions().disable(); // TODO: Enable it in production
    }
  }

  // The httpSecurity.antMatcher states that this HttpSecurity will only be applicable to URLs that start with /api/
  // With @Order, this adapter should be considered first.
  @Configuration
  @Order(1)
  public static class ApiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
          .antMatcher("/api/**")
          .authorizeRequests()
              .anyRequest().hasAuthority("ADMIN")
              .and()
          .httpBasic();
    }
  }
}