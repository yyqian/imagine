package com.yyqian.imagine.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by yyqian on 12/15/15.
 */
public class CommentCreateForm {

  @NotNull
  private Long postId;

  @NotEmpty
  private String content = "";

  private Long parentId;

  public CommentCreateForm() {
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content.trim();
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

}
