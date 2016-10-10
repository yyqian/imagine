package com.yyqian.imagine.service.impl;

import com.yyqian.imagine.dto.CommentCreateForm;
import com.yyqian.imagine.exception.NotFoundException;
import com.yyqian.imagine.po.Comment;
import com.yyqian.imagine.po.Post;
import com.yyqian.imagine.po.User;
import com.yyqian.imagine.repository.CommentRepository;
import com.yyqian.imagine.service.CommentService;
import com.yyqian.imagine.service.PostService;
import com.yyqian.imagine.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private PostService postService;
  @Autowired
  private SecurityService securityService;

  @Override
  public Optional<Comment> getCommentById(long id) {
    return Optional.ofNullable(commentRepository.findOne(id));
  }

  @Override
  public Collection<Comment> getCommentTreeByPost(Post post) {
    return commentRepository.findRootsByPost(post);
  }

  @Override
  public Page<Comment> getCommentListByPage(Pageable pageable) {
    return commentRepository.findAll(pageable);
  }

  @Override
  public Collection<Comment> getCommentListByCurrentUser() {
    if (!securityService.isLoggedIn()) {
      return Collections.emptyList();
    }
    return commentRepository.findByUser(securityService.getUser());
  }

  @Transactional
  @Override
  public Comment create(Comment comment) {
    Post post = postService.getPostById(comment.getPost().getId()).orElseThrow(
        () -> new NotFoundException("Cannot find the post."));
    post.increaseCommentCount();
    postService.update(post);
    return commentRepository.save(comment);
  }

  @Transactional
  @Override
  public Comment update(Comment comment) {
    return commentRepository.save(comment);
  }

  @Transactional
  @Override
  public Comment create(CommentCreateForm form) {
    String content = form.getContent();
    User user = securityService.getUser();
    Post post = postService.getPostById(form.getPostId()).orElseThrow(
        () -> new NotFoundException("Cannot find the post."));
    Comment comment;
    if (form.getParentId() != null) {
      Comment parent = commentRepository.findOne(form.getParentId());
      comment = new Comment(content, user, post, parent);
    } else {
      comment = new Comment(content, user, post);
    }
    post.increaseCommentCount();
    postService.update(post);
    return commentRepository.save(comment);
  }

}
