package com.yyqian.imagine.service.impl;

import com.yyqian.imagine.po.User;
import com.yyqian.imagine.repository.UserRepository;
import com.yyqian.imagine.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.NoSuchElementException;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
@Service
public class SecurityServiceImpl implements SecurityService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public String getUsername() {
    String username;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      username = ((UserDetails)principal).getUsername();
    } else {
      username = principal.toString();
    }
    return username;
  }

  @Override
  public User getUser() {
    return userRepository.findOneByUsername(getUsername()).orElseThrow(
        () -> new NoSuchElementException("User not found"));
  }

  @Override
  public String getAuthorities() {
    String authorities;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      authorities = ((UserDetails)principal).getAuthorities().toString();
    } else {
      authorities = principal.toString();
    }
    return authorities;
  }

  @Override
  public boolean isLoggedIn() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return !(auth instanceof AnonymousAuthenticationToken);
  }

}
