package com.yyqian.imagine.po;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created on 12/14/15.
 *
 * @author Yinyin Qian
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class BaseUGC extends Base implements Serializable {

  /**
   * Lob annotation tells the persistence provider that content is bound to a large object type
   * column.
   */
  @Lob
  @Column(name = "content", nullable = false)
  protected String content;

  /**
   * One User can have many Posts, and one Post can only have one User. So it's ManyToOne relation.
   * NotFound with NotFoundAction.IGNORE: You can ask Hibernate to ignore bad elements instead
   * of raising an exception.
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @NotNull
  protected User user;

  @Column(name = "up_vote_count", nullable = false)
  protected int upVoteCount = 0;

  @Column(name = "down_vote_count", nullable = false)
  protected int downVoteCount = 0;

  @Transient
  protected int point;

  public BaseUGC() {
  }

  public BaseUGC(String content, User user) {
    this.content = content;
    this.user = user;
  }

  public int getPoint() {
    return (upVoteCount - downVoteCount);
  }

  public String getContent() {
    return content;
  }

  public User getUser() {
    return user;
  }

  public int getUpVoteCount() {
    return upVoteCount;
  }

  public int getDownVoteCount() {
    return downVoteCount;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void increaseUpVoteCount() {
    upVoteCount = upVoteCount + 1;
  }

  public void increaseDownVoteCount() {
    downVoteCount = downVoteCount + 1;
  }

  public void decreaseUpVoteCount() {
    if (upVoteCount > 0) {
      upVoteCount = upVoteCount - 1;
    }
  }

  public void decreaseDownVoteCount() {
    if (downVoteCount > 0) {
      downVoteCount = downVoteCount - 1;
    }
  }

}
