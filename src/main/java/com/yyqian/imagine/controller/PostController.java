package com.yyqian.imagine.controller;

import com.yyqian.imagine.constant.UriConstant;
import com.yyqian.imagine.dto.PostCreateForm;
import com.yyqian.imagine.dto.validator.PostCreateFormValidator;
import com.yyqian.imagine.exception.NotFoundException;
import com.yyqian.imagine.po.Post;
import com.yyqian.imagine.service.CommentService;
import com.yyqian.imagine.service.PostService;
import com.yyqian.imagine.service.SecurityService;
import com.yyqian.imagine.service.VoteService;
import com.yyqian.imagine.utility.ControllerUtil;

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

import java.util.NoSuchElementException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by yyqian on 12/5/15.
 * PostController
 * Don't impend PUT, PATCH, DELETE methods in regular controller
 * Don't redirect a DELETE request to a GET request
 * APIs:
 * GET /post
 * GET /post/{id}
 * POST /post
 * TODO: Check exception
 */
@Controller
public class PostController {

  private final PostService postService;
  private final CommentService commentService;
  private final ControllerUtil controllerUtil;
  private final SecurityService securityService;
  private final VoteService voteService;
  private final PostCreateFormValidator postCreateFormValidator;

  @Autowired
  public PostController(PostService postService, CommentService commentService,
                        ControllerUtil controllerUtil, SecurityService securityService,
                        VoteService voteService, PostCreateFormValidator postCreateFormValidator) {
    this.postService = postService;
    this.commentService = commentService;
    this.controllerUtil = controllerUtil;
    this.securityService = securityService;
    this.voteService = voteService;
    this.postCreateFormValidator = postCreateFormValidator;
  }

  @InitBinder("postCreateForm")
  public void initBinder(WebDataBinder binder) {
    binder.addValidators(postCreateFormValidator);
  }

  @RequestMapping(value = UriConstant.POST_EDITOR, method = GET)
  public String editor(Model model) {
    controllerUtil.addLoginInfo(model);
    return "post-editor";
  }

  @RequestMapping(value = UriConstant.POST, method = POST)
  public String create(@Valid @ModelAttribute("postCreateForm") PostCreateForm postCreateForm) {
    postService.create(postCreateForm);
    return "redirect:" + UriConstant.POST;
  }

  @RequestMapping(value = UriConstant.POST, method = GET)
  public String list(@RequestParam(value = "p", required = false) Integer page,
                     @RequestParam(value = "s", required = false) Integer size,
                     @RequestParam(value = "site", required = false) String site,
                     @RequestParam(value = "self", required = false) Boolean self, Model model) {
    boolean listBySite = (site != null) && (!site.isEmpty());
    boolean listByCurrentUser = (self != null) && self;
    if (listBySite) {
      model.addAttribute("posts", postService.getPostListBySite(site));
    } else if (listByCurrentUser) {
      model.addAttribute("posts", postService.getPostListByCurrentUser());
    } else {
      if (page == null || page < 1) {
        page = 1;
      }
      if (size == null || size < 1) {
        size = 20;
      }
      Pageable pageRequest = new PageRequest(page - 1, size, Sort.Direction.DESC, "updatedAt");
      Page<Post> posts = postService.getPostListByPage(pageRequest);
      model.addAttribute("posts", posts)
           .addAttribute("page", page)
           .addAttribute("size", size);
      if (page < posts.getTotalPages()) {
        model.addAttribute("nextPage", page + 1);
      }
    }
    boolean isLoggedIn = securityService.isLoggedIn();
    model.addAttribute("isLoggedIn", isLoggedIn);
    if (isLoggedIn) {
      model.addAttribute("username", securityService.getUsername())
           .addAttribute("votedPosts", voteService.getPostsVotedByCurrentUser());
    }
    return "post-list";
  }

  @RequestMapping(value = UriConstant.POST + "/{id:\\d+}", method = GET)
  public String read(@PathVariable("id") Long id, Model model) {
    Post post = postService.getPostById(id)
                           .orElseThrow(() -> new NotFoundException(
                               "The post you requested does not exist!"));
    boolean isLoggedIn = securityService.isLoggedIn();
    model.addAttribute("post", post)
         .addAttribute("comments", commentService.getCommentTreeByPost(post))
         .addAttribute("isLoggedIn", isLoggedIn);
    if (isLoggedIn) {
      model.addAttribute("username", securityService.getUsername())
           .addAttribute("postIsVoted", voteService.isPostVotedByCurrentUser(post))
           .addAttribute("votedComments", voteService.getCommentsVotedByCurrentUser());
    }
    return "post";
  }

}
