package com.yyqian.imagine.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yyqian on 12/16/15.
 */
public class PostCreateForm {

  @NotEmpty
  private String title = "";

  private String url;

  private String text;

  public PostCreateForm() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title.trim();
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url.trim();
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text.trim();
  }

}
