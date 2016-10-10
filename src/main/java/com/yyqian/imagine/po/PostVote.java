package com.yyqian.imagine.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by yyqian on 12/12/15.
 * A composite primary key class has the following characteristics:
 * - It is a POJO class.
 * - It must be public and must have a public no-argument constructor.
 * - If you use property-based access, the properties of the primary key class must be public or
 * protected.
 * - It must be serializable.
 * - It must define equals and hashCode methods.
 */
// TODO: implant IdClass
@Entity
@NamedQueries(value = {@NamedQuery(name = "PostVote.findPostsVotedByUser", query =
    "SELECT pv.post FROM PostVote pv WHERE pv.user = ?1")})
@Table(name = "post_vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id",
    "post_id"})})
public class PostVote extends BaseVote {

  @EmbeddedId
  private PostVotePK postVotePK;

  // If we apply the @Id on a class, this class must be serializable, should have equals(),
  // hashcode()
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId(value = "userId")
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  @NotNull
  @JsonIgnore
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId(value = "postId")
  @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
  @NotNull
  @JsonIgnore
  private Post post;

  public PostVote() {
  }

  public PostVote(Post post, User user, int score) {
    super(score);
    this.user = user;
    this.post = post;
    this.postVotePK = new PostVotePK(user.getId(), post.getId());
  }

  public User getUser() {
    return user;
  }

  public Post getPost() {
    return post;
  }

  public PostVotePK getPostVotePK() {
    return postVotePK;
  }
}
