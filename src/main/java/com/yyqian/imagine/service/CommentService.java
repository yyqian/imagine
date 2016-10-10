package com.yyqian.imagine.service;

import com.yyqian.imagine.dto.CommentCreateForm;
import com.yyqian.imagine.po.Comment;
import com.yyqian.imagine.po.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
public interface CommentService {

  Comment create(Comment comment);

  Comment create(CommentCreateForm form);

  Comment update(Comment comment);

  Optional<Comment> getCommentById(long id);

  Collection<Comment> getCommentTreeByPost(Post post);

  Page<Comment> getCommentListByPage(Pageable pageable);

  Collection<Comment> getCommentListByCurrentUser();

}
