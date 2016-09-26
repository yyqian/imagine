package com.yyqian.imagine.po;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by yyqian on 12/13/15.
 */
@Embeddable
public class PostVotePK implements Serializable {

  public PostVotePK() {
  }

  public PostVotePK(Long userId, Long postId) {
    this.userId = userId;
    this.postId = postId;
  }

  private Long userId;

  private Long postId;

  public Long getUserId() {
    return userId;
  }

  public Long getPostId() {
    return postId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if ((null == o) || (o.getClass() != getClass())) {
      return false;
    }
    final PostVotePK pvPK = (PostVotePK) o;
    return (pvPK.userId.equals(userId)) && (pvPK.postId.equals(postId));
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(userId)
        .append(postId)
        .toHashCode();
  }

}
