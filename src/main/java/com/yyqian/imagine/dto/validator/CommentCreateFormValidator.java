package com.yyqian.imagine.dto.validator;

import com.yyqian.imagine.dto.CommentCreateForm;
import com.yyqian.imagine.service.CommentService;
import com.yyqian.imagine.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created on 1/8/16.
 *
 * @author Yinyin Qian
 */
@Component
public class CommentCreateFormValidator implements Validator {

  @Autowired
  private PostService postService;

  @Autowired
  private CommentService commentService;

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.equals(CommentCreateForm.class);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CommentCreateForm form = (CommentCreateForm)target;
    if (!postService.getPostById(form.getPostId()).isPresent()) {
      errors.reject("postId.exists", "postId does not exist");
    }
    if (form.getParentId() != null) {
      if (!commentService.getCommentById(form.getParentId()).isPresent()) {
        errors.reject("parentId.exists", "parentId does not exist");
      }
    }
  }
}
