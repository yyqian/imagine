package com.yyqian.imagine.constant;

/**
 * Created on 26/09/2016.
 *
 * @author Yinyin Qian
 */
public final class UriConstant {

  private UriConstant() {
  }

  public static final String ABOUT = "/about";
  public static final String LOGIN = "/login";
  public static final String LOGIN_ERROR = LOGIN + "?error";
  public static final String LOGOUT = "/logout";
  public static final String FORGOT = "/forgot";
  public static final String RESETPW = "/resetpw";

  public static final String POST = "/post";
  public static final String POST_EDITOR = POST + "/new/editor";
  public static final String COMMENT = "/comment";
  public static final String USER = "/user";
  public static final String USER_SELF = USER + "/self";
}
