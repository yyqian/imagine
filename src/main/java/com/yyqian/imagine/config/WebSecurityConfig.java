package com.yyqian.imagine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.yyqian.imagine.constant.UriConstant.*;

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
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Configuration
  public static class DefaultWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
          .authorizeRequests()
              .antMatchers(HttpMethod.GET, POST_EDITOR, USER_SELF).authenticated()
              .antMatchers(FORGOT, USER, LOGIN).permitAll()
              .antMatchers(HttpMethod.GET, "/js/**", "/css/**", "/images/**", "**/favicon.ico").permitAll()
              .antMatchers(HttpMethod.GET, RESETPW + "/**", POST, POST + "/**", COMMENT, COMMENT + "/**", USER + "/**", "/", ABOUT, LOGOUT).permitAll()
              .antMatchers("/h2-console/**").permitAll()
              .anyRequest().authenticated()
              .and()
          .formLogin()
              .loginPage(LOGIN)
              .failureUrl(LOGIN_ERROR)
              .usernameParameter("username")
              .permitAll()
              .and()
          .logout()
              .logoutSuccessUrl("/").permitAll();
      if (activeProfile.equals("debug")) {
        activeH2DBSupport(httpSecurity);
      }
    }

    /**
     * Call this function to active the web console of H2 database, this should not be activated in
     * prod environment
     */
    private void activeH2DBSupport(HttpSecurity httpSecurity) throws Exception {
      httpSecurity.csrf().disable(); // Needed for H2 Database
      httpSecurity.headers().frameOptions().disable(); // Needed for H2 Database
    }
  }

}