package com.yyqian.imagine.utility;

/**
 * Created by yyqian on 12/16/15.
 */
public class StringUtility {
  public static String getSite(String url) {
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
      throw new IllegalArgumentException("The url is incorrect.");
    }
    String[] siteParts = (url.split("/")[2]).split("\\.");
    String site;
    if (siteParts.length < 2) {
      throw new IllegalArgumentException("The url is incorrect.");
    } else if (siteParts.length == 2) {
      site = siteParts[0] + "." + siteParts[1];
    } else if (siteParts[1].equals("com")) {
      site = siteParts[0] + "." + siteParts[1] + "." + siteParts[2];
    } else {
      site = siteParts[1] + "." + siteParts[2];
    }
    return site;
  }
}
