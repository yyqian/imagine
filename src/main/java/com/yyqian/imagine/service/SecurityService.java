package com.yyqian.imagine.service;

import com.yyqian.imagine.po.User;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
public interface SecurityService {

  String getUsername();

  String getAuthorities();

  User getUser();

  boolean isLoggedIn();

}
