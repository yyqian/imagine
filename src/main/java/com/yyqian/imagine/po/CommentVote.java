package com.yyqian.imagine.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by yyqian on 12/12/15.
 */
@Entity
@NamedQueries(value = {
    @NamedQuery(name = "CommentVote.findCommentsVotedByUser",
        query = "SELECT cv.comment FROM CommentVote cv WHERE cv.user = ?1")
})
@Table(name = "comment_vote",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "comment_id"})})
public class CommentVote extends BaseVote {

  @EmbeddedId
  private CommentVotePK commentVotePK;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId(value = "userId")
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  @NotNull
  @JsonIgnore
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId(value = "commentId")
  @JoinColumn(name = "comment_id", referencedColumnName = "id", insertable = false, updatable = false)
  @NotNull
  @JsonIgnore
  private Comment comment;

  public CommentVote() {
  }

  public CommentVote(Comment comment, User user, int score) {
    super(score);
    this.user = user;
    this.comment = comment;
    this.commentVotePK = new CommentVotePK(user.getId(), comment.getId());
  }

  public User getUser() {
    return user;
  }

  public Comment getComment() {
    return comment;
  }

  public CommentVotePK getCommentVotePK() {
    return commentVotePK;
  }
}
