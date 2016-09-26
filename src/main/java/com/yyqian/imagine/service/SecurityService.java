package com.yyqian.imagine.service;

import com.yyqian.imagine.po.User;

/**
 * Created by yyqian on 12/15/15.
 */
public interface SecurityService {

  String getUsername();

  String getAuthorities();

  User getUser();

  boolean isLoggedIn();

}
