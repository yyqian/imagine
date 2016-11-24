package com.yyqian.imagine.po;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yyqian on 12/9/15.
 * Comment
 */
@Entity
@NamedQueries(value = {@NamedQuery(name = "Comment.findRootsByPost", query =
    "SELECT c FROM Comment c WHERE c.parent IS NULL AND c.post = ?1")})
@Table(name = "comment")
public class Comment extends BaseUGC {

  // map to post, bidirectional
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  @NotNull
  @JsonIgnore
  private Post post;

  // with JsonBackReference, instance will not fetch details about the parent
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_comment_id")
  @JsonBackReference
  private Comment parent;

  // with JsonManagedReference, instance will try to fetch all details about nested children
  @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true, cascade =
      {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonManagedReference
  private List<Comment> children = new ArrayList<Comment>();

  public Comment() {
  }

  public Comment(String content, User user, Post post) {
    super(content, user);
    this.post = post;
  }

  public Comment(String content, User user, Post post, Comment parent) {
    super(content, user);
    this.post = post;
    this.parent = parent;
  }

  // attach a child comment
  public void attachChild(Comment comment) {
    if (comment == null) {
      throw new IllegalArgumentException("child comment is null!");
    }
    // if comment was attached to another parent comment, remove it from its parent's children
    if (comment.getParent() != null) {
      if (comment.getParent().equals(this)) {
        return;
      } else {
        comment.getParent().children.remove(comment);
      }
    }
    comment.setParent(this);
    children.add(comment);
  }

  // detach a child comment
  public void detachChild(Comment comment) {
    if (comment == null) {
      throw new IllegalArgumentException("child comment is null!");
    }

    if (comment.parent != null && comment.getParent().equals(this)) {
      comment.setParent(null);
      children.remove(comment);
    } else {
      throw new IllegalArgumentException("child comment not associated with this instance");
    }
  }

  @Override
  public String toString() {
    return id.toString();
  }

  public Post getPost() {
    return post;
  }

  public Comment getParent() {
    return parent;
  }

  public List<Comment> getChildren() {
    // make it an immutable list
    return Collections.unmodifiableList(children);
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public void setUser(User user) {
    this.user = user;
  }

  private void setParent(Comment parent) {
    this.parent = parent;
  }

}
