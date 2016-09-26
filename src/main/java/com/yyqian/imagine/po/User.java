package com.yyqian.imagine.po;

import javax.persistence.*;

/**
 * Created by yyqian on 12/9/15.
 * User
 */
@Entity
@Table(name = "user")
public class User extends Base {

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role = Role.USER;

  @Lob
  @Column(name = "about")
  private String about;

  @Column(name = "point", nullable = false)
  private Integer point = 0;

  public enum Role {
    USER, ADMIN
  }

  public User() {
  }

  public User(String username, String passwordHash) {
    this.username = username;
    this.passwordHash = passwordHash;
  }

  @Override
  public String toString() {
    return id.toString();
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public Role getRole() {
    return role;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public Integer getPoint() {
    return point;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }
}
