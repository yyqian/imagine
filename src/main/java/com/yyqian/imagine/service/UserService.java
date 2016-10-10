package com.yyqian.imagine.service;

import com.yyqian.imagine.dto.PasswordRestForm;
import com.yyqian.imagine.dto.UserCreateForm;
import com.yyqian.imagine.dto.UserUpdateForm;
import com.yyqian.imagine.po.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
public interface UserService {

  Optional<User> getUserById(long id);

  Optional<User> getUserByUsername(String username);

  Collection<User> getAllUsers();

  User create(UserCreateForm form);

  int increasePointByUserId(Long id);

  User update(UserUpdateForm form);

  User resetPassword(PasswordRestForm form);

  void sendRestPasswordEmail(String username);

  void authUserByToken(String token);

}
