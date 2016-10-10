package com.yyqian.imagine.controller;

import com.yyqian.imagine.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created on 12/16/15.
 *
 * @author Yinyin Qian
 */
@RestController
public class VoteController {

  private final VoteService voteService;

  @Autowired
  public VoteController(VoteService voteService) {
    this.voteService = voteService;
  }

  @RequestMapping(value = "/post/{id:\\d+}/point", method = POST)
  public String upVotePost(@PathVariable(value = "id") Long id) {
    voteService.upVotePostById(id);
    return "ok";
  }

  @RequestMapping(value = "/comment/{id:\\d+}/point", method = POST)
  public String upVoteComment(@PathVariable(value = "id") Long id) {
    voteService.upVoteCommentById(id);
    return "ok";
  }
}
