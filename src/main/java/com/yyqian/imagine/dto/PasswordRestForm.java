package com.yyqian.imagine.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by yyqian on 1/9/16.
 */
public class PasswordRestForm {

  @NotEmpty
  @Size(min = 6, max = 20)
  private String password = "";

  public PasswordRestForm() {
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
