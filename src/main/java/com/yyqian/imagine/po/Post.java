package com.yyqian.imagine.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyqian on 12/5/15.
 * we can use @AttributeOverrides to override the inherited column names: id, version,
 * created_at, updated_at
 */
@Entity
@Table(name = "post")
public class Post extends BaseUGC {

  @Column(name = "title", nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(name = "kind", nullable = false)
  private Kind kind;

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private Category category;

  // map to comment, bidirectional
  // @OneToMany tells the persistence provider this is an association and not an attribute.
  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, orphanRemoval = true, cascade =
      {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonIgnore
  private List<Comment> comments = new ArrayList<Comment>();

  @Column(name = "comment_count", nullable = false)
  private int commentCount = 0;

  @Column(name = "site")
  private String site;

  // The default constructor only exists for the sake of JPA. You won’t use it directly, so it is
  // designated as protected.
  public Post() {
  }

  public Post(String title, String content, User user, Kind kind) {
    super(content, user);
    this.title = title;
    this.kind = kind;
  }

  @Override
  public String toString() {
    return id.toString();
  }

  public static enum Kind {PLAIN, LINK}

  public static enum Category {SHOW, ASK}

  public String getTitle() {
    return title;
  }

  public Kind getKind() {
    return kind;
  }

  public Category getCategory() {
    return category;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public int getCommentCount() {
    return commentCount;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setKind(Kind kind) {
    this.kind = kind;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public void increaseCommentCount() {
    commentCount = commentCount + 1;
  }

  public void decreaseCommentCount() {
    if (commentCount > 0) {
      commentCount = commentCount - 1;
    }
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }
}
