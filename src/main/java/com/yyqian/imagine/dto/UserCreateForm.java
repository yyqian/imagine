package com.yyqian.imagine.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yyqian on 12/15/15.
 * This will function as a data transfer object (DTO) between the web layer and service layer.
 */
public class UserCreateForm {

  @NotEmpty
  private String username = "";

  @NotEmpty
  private String password = "";

  @NotEmpty
  private String passwordRepeated = "";

  public UserCreateForm() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username.trim();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password.trim();
  }

  public String getPasswordRepeated() {
    return passwordRepeated;
  }

  public void setPasswordRepeated(String passwordRepeated) {
    this.passwordRepeated = passwordRepeated.trim();
  }

}