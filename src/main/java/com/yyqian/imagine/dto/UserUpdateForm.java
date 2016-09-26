package com.yyqian.imagine.dto;

/**
 * Created by yyqian on 12/16/15.
 */
public class UserUpdateForm {

  private String email = "";

  private String about = "";

  public UserUpdateForm() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email.trim();
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about.trim();
  }

}
