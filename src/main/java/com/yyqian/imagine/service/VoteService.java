package com.yyqian.imagine.service;

import com.yyqian.imagine.po.Comment;
import com.yyqian.imagine.po.CommentVote;
import com.yyqian.imagine.po.Post;
import com.yyqian.imagine.po.PostVote;

import java.util.Collection;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
public interface VoteService {

  PostVote createPostVote(PostVote postVote);

  CommentVote createCommentVote(CommentVote commentVote);

  PostVote upVotePostById(Long id);

  PostVote downVotePostById(Long id);

  CommentVote upVoteCommentById(Long id);

  CommentVote downVoteCommentById(Long id);

  boolean isPostVotedByCurrentUser(Post post);

  boolean isCommentVotedByCurrentUser(Comment comment);

  Collection<Post> getPostsVotedByCurrentUser();

  Collection<Comment> getCommentsVotedByCurrentUser();

}
