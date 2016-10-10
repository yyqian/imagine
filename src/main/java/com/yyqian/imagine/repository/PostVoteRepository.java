package com.yyqian.imagine.repository;

import com.yyqian.imagine.po.Post;
import com.yyqian.imagine.po.PostVote;
import com.yyqian.imagine.po.PostVotePK;
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
public interface PostVoteRepository extends JpaRepository<PostVote, PostVotePK> {
  Optional<PostVote> findOneByUserAndPost(User user, Post post);
  Collection<Post> findPostsVotedByUser(User user);
}
