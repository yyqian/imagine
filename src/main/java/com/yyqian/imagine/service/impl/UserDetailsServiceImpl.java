package com.yyqian.imagine.service.impl;

import com.yyqian.imagine.po.User;
import com.yyqian.imagine.repository.UserRepository;
import com.yyqian.imagine.vo.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findOneByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException(
            String.format("User with username=%s was not found", username)));
    return new CurrentUser(user);
  }

}