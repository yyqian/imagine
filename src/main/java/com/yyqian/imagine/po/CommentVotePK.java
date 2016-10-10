package com.yyqian.imagine.po;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created on 12/13/15.
 *
 * @author Yinyin Qian
 */
public class CommentVotePK implements Serializable {

  public CommentVotePK() {
  }

  public CommentVotePK(Long userId, Long commentId) {
    this.userId = userId;
    this.commentId = commentId;
  }

  private Long userId;
  private Long commentId;

  public Long getUserId() {
    return userId;
  }

  public Long getCommentId() {
    return commentId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if ((null == o) || (o.getClass() != getClass())) {
      return false;
    }
    final CommentVotePK cvPK = (CommentVotePK)o;
    return (cvPK.userId.equals(userId)) && (cvPK.commentId.equals(commentId));
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(userId).append(commentId).toHashCode();
  }

}
