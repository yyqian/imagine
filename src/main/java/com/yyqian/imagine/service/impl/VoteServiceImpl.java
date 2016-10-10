package com.yyqian.imagine.service.impl;

import com.yyqian.imagine.exception.BadRequestException;
import com.yyqian.imagine.exception.NotFoundException;
import com.yyqian.imagine.po.*;
import com.yyqian.imagine.repository.CommentVoteRepository;
import com.yyqian.imagine.repository.PostVoteRepository;
import com.yyqian.imagine.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
@Service
public class VoteServiceImpl implements VoteService {

  @Autowired
  private PostService postService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private PostVoteRepository postVoteRepository;
  @Autowired
  private CommentVoteRepository commentVoteRepository;
  @Autowired
  private SecurityService securityService;
  @Autowired
  private UserService userService;

  @Transactional
  @Override
  public PostVote createPostVote(PostVote postVote) {
    Post post = postService.getPostById(postVote.getPost().getId()).orElseThrow(
        () -> new NotFoundException("Cannot find the post."));
    if (0 < postVote.getScore()) {
      post.increaseUpVoteCount();
      userService.increasePointByUserId(post.getUser().getId());
    } else if (0 > postVote.getScore()) {
      post.increaseDownVoteCount();
    }
    postService.update(post);
    return postVoteRepository.save(postVote);
  }

  @Transactional
  @Override
  public CommentVote createCommentVote(CommentVote commentVote) {
    Comment comment = commentService.getCommentById(commentVote.getComment().getId()).orElseThrow(
        () -> new NotFoundException("Cannot find the comment."));
    if (0 < commentVote.getScore()) {
      comment.increaseUpVoteCount();
      userService.increasePointByUserId(comment.getUser().getId());
    } else if (0 > commentVote.getScore()) {
      comment.increaseDownVoteCount();
    }
    commentService.update(comment);
    return commentVoteRepository.save(commentVote);
  }

  @Override
  public boolean isPostVotedByCurrentUser(Post post) {
    return (postVoteRepository.findOneByUserAndPost(securityService.getUser(), post) != null);
  }

  @Override
  public boolean isCommentVotedByCurrentUser(Comment comment) {
    return (commentVoteRepository.findOneByUserAndComment(securityService.getUser(),
                                                          comment) != null);
  }

  @Override
  public Collection<Post> getPostsVotedByCurrentUser() {
    return postVoteRepository.findPostsVotedByUser(securityService.getUser());
  }

  @Override
  public Collection<Comment> getCommentsVotedByCurrentUser() {
    return commentVoteRepository.findCommentsVotedByUser(securityService.getUser());
  }

  @Override
  public PostVote upVotePostById(Long id) {
    return votePostById(id, 1);
  }

  @Override
  public PostVote downVotePostById(Long id) {
    return votePostById(id, -1);
  }

  @Override
  public CommentVote upVoteCommentById(Long id) {
    return voteCommentById(id, 1);
  }

  @Override
  public CommentVote downVoteCommentById(Long id) {
    return voteCommentById(id, -1);
  }

  @Transactional
  public PostVote votePostById(Long id, int score) {
    Post post = postService.getPostById(id).orElseThrow(
        () -> new NoSuchElementException("Cannot find the post."));
    User user = securityService.getUser();
    if (postVoteRepository.findOneByUserAndPost(user, post).isPresent()) {
      throw new BadRequestException("You already voted.");
    }
    if (score > 0) {
      post.increaseUpVoteCount();
      postService.update(post);
      userService.increasePointByUserId(post.getUser().getId());
      score = 1;
    } else if (score < 0) {
      post.increaseDownVoteCount();
      postService.update(post);
      score = -1;
    } else {
      score = 0;
    }
    return postVoteRepository.save(new PostVote(post, user, score));
  }

  @Transactional
  public CommentVote voteCommentById(Long id, int score) {
    Comment comment = commentService.getCommentById(id).orElseThrow(
        () -> new NoSuchElementException("Cannot find the comment."));
    User user = securityService.getUser();
    if (commentVoteRepository.findOneByUserAndComment(user, comment).isPresent()) {
      throw new BadRequestException("You already voted.");
    }
    if (score > 0) {
      comment.increaseUpVoteCount();
      commentService.update(comment);
      userService.increasePointByUserId(comment.getUser().getId());
      score = 1;
    } else if (score < 0) {
      comment.increaseDownVoteCount();
      commentService.update(comment);
      score = -1;
    } else {
      score = 0;
    }
    return commentVoteRepository.save(new CommentVote(comment, user, score));
  }

}
