package com.yyqian.imagine.repository;

import com.yyqian.imagine.po.Comment;
import com.yyqian.imagine.po.CommentVote;
import com.yyqian.imagine.po.CommentVotePK;
import com.yyqian.imagine.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created on 12/13/15.
 *
 * @author Yinyin Qian
 */
@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, CommentVotePK> {
  Optional<CommentVote> findOneByUserAndComment(User user, Comment comment);
  Collection<Comment> findCommentsVotedByUser(User user);
}
