package com.yyqian.imagine.repository;

import com.yyqian.imagine.po.Comment;
import com.yyqian.imagine.po.Post;
import com.yyqian.imagine.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created on 12/9/15.
 *
 * @author Yinyin Qian
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  Collection<Comment> findRootsByPost(Post post);
  Collection<Comment> findByUser(User user);
}
