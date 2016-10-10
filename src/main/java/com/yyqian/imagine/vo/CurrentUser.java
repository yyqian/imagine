package com.yyqian.imagine.vo;

import com.yyqian.imagine.po.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by yyqian on 12/15/15.
 * Get the user in the session
 * Value Object?
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

  private User user;

  public CurrentUser(User user) {
    super(user.getUsername(), user.getPasswordHash(),
          AuthorityUtils.createAuthorityList(user.getRole().toString()));
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public Long getId() {
    return user.getId();
  }

  public User.Role getRole() {
    return user.getRole();
  }
}
