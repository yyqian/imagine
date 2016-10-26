package com.yyqian.imagine.controller;

import com.yyqian.imagine.dto.CommentCreateForm;
import com.yyqian.imagine.dto.validator.CommentCreateFormValidator;
import com.yyqian.imagine.exception.NotFoundException;
import com.yyqian.imagine.po.Comment;
import com.yyqian.imagine.service.CommentService;
import com.yyqian.imagine.service.SecurityService;
import com.yyqian.imagine.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.yyqian.imagine.constant.UriConstant.COMMENT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created on 12/15/15.
 * All comments related request mapping
 * @author Yinyin Qian
 */
@Controller
public class CommentController {

  private final CommentService commentService;
  private final SecurityService securityService;
  private final VoteService voteService;
  private final CommentCreateFormValidator commentCreateFormValidator;

  @Autowired
  public CommentController(CommentService commentService, SecurityService securityService,
                           VoteService voteService,
                           CommentCreateFormValidator commentCreateFormValidator) {
    this.commentService = commentService;
    this.securityService = securityService;
    this.voteService = voteService;
    this.commentCreateFormValidator = commentCreateFormValidator;
  }

  @InitBinder("commentCreateForm")
  public void initBinder(WebDataBinder binder) {
    binder.addValidators(commentCreateFormValidator);
  }

  /**
   * show the list of comments based on page, size
   * if self == true, only show comments posted by the user self.
   */
  @RequestMapping(value = COMMENT, method = GET)
  public String list(@RequestParam(value = "p", required = false) Integer page,
                     @RequestParam(value = "s", required = false) Integer size,
                     @RequestParam(value = "self", required = false) Boolean self, Model model) {
    boolean listByCurrentUser = (self != null) && self;
    if (page == null || page < 1) {
      page = 1;
    }
    if (size == null || size < 1) {
      size = 20;
    }
    boolean isLoggedIn = securityService.isLoggedIn();
    if (listByCurrentUser) {
      model.addAttribute("comments", commentService.getCommentListByCurrentUser());
    } else {
      Pageable pageRequest = new PageRequest(page - 1, size, Sort.Direction.DESC, "updatedAt");
      Page<Comment> comments = commentService.getCommentListByPage(pageRequest);
      model.addAttribute("comments", comments)
           .addAttribute("size", size)
           .addAttribute("page", page);
      if (page < comments.getTotalPages()) {
        model.addAttribute("nextPage", page + 1);
      }
    }
    model.addAttribute("isLoggedIn", isLoggedIn);
    if (isLoggedIn) {
      model.addAttribute("username", securityService.getUsername())
           .addAttribute("votedComments", voteService.getCommentsVotedByCurrentUser());
    }
    return "comment-list";
  }

  @RequestMapping(value = COMMENT, method = POST)
  public String create(
      @Valid @ModelAttribute("commentCreateForm") CommentCreateForm commentCreateForm) {
    commentService.create(commentCreateForm);
    return "redirect:/post/" + commentCreateForm.getPostId();
  }

  @RequestMapping(value = COMMENT + "/{id:\\d+}", method = GET)
  public String read(@PathVariable("id") Long id, Model model) {
    model.addAttribute("isLoggedIn", securityService.isLoggedIn())
         .addAttribute("username", securityService.getUsername())
         .addAttribute("comment", commentService.getCommentById(id)
                                                .orElseThrow(() -> new NotFoundException(
                                                    "Cannot find the comment")));
    return "comment";
  }

}
