package com.yyqian.imagine.utility;

import com.yyqian.imagine.dto.UserCreateForm;
import com.yyqian.imagine.dto.UserUpdateForm;
import com.yyqian.imagine.po.*;
import com.yyqian.imagine.service.CommentService;
import com.yyqian.imagine.service.PostService;
import com.yyqian.imagine.service.UserService;
import com.yyqian.imagine.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by yyqian on 12/5/15.
 * runs in dev env
 */
@Component
@Profile(value = {"dev", "debug"})
public class RockAndRoll implements CommandLineRunner {

  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;

  @Autowired
  private CommentService commentService;

  @Autowired
  private VoteService voteService;

  private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  private List<User> users = new ArrayList<>();
  private List<Post> posts = new ArrayList<>();
  private List<Comment> comments = new ArrayList<>();
  private final int userLength = 10;
  private final int postHalfLength = 25;
  private final int postLength = postHalfLength * 2;
  private final int commentQuarterLength = 100;
  private final int commentLength = commentQuarterLength * 4;

  public void run(String... args) {
    logger.info("Generating sample-data");
    genUsers();
    genPosts();
    genComments();
    genVotes();
    logger.info("Data generated. Let's Rock!");
  }

  private void genUsers() {
    UserCreateForm form = new UserCreateForm();
    String password = "123123";
    form.setUsername("shadow");
    form.setPassword(password);
    form.setPasswordRepeated(password);
    users.add(userService.create(form));
    IntStream.range(2, userLength).forEach(i -> {
      form.setUsername(RandomGenerator.genLCCharStr(5));
      users.add(userService.create(form));
    });
    form.setUsername("demo");
    users.add(userService.create(form));
  }

  private void genPosts() {
    IntStream.range(0, postHalfLength).forEach(i -> {
      posts.add(postService.create(
          new Post(genTitle(), genText(), users.get(genInt(userLength)), Post.Kind.PLAIN)));
      String url = genLink();
      Post post = new Post(genTitle(), url, users.get(genInt(userLength)), Post.Kind.LINK);
      post.setSite(StringUtility.getSite(url));
      posts.add(postService.create(post));
    });
  }

  private void genComments() {
    IntStream.range(0, commentQuarterLength).forEach(i -> {
      comments.add(commentService.create(
          new Comment(genText(), users.get(genInt(userLength)), posts.get(genInt(postLength)))));
    });
    IntStream.range(0, commentQuarterLength).forEach(i -> {
      Comment parent = comments.get(genInt(commentQuarterLength));
      comments.add(commentService.create(
          new Comment(genText(), users.get(genInt(userLength)), parent.getPost(), parent)));
    });
    IntStream.range(0, commentQuarterLength).forEach(i -> {
      Comment parent = comments.get(genInt(2 * commentQuarterLength));
      comments.add(commentService.create(
          new Comment(genText(), users.get(genInt(userLength)), parent.getPost(), parent)));
    });
    IntStream.range(0, commentQuarterLength).forEach(i -> {
      Comment parent = comments.get(genInt(3 * commentQuarterLength));
      comments.add(commentService.create(
          new Comment(genText(), users.get(genInt(userLength)), parent.getPost(), parent)));
    });
  }

  private void genVotes() {
    List<User> partUsers = users.subList(0, userLength / 2);
    List<Post> partPosts = posts.subList(0, postHalfLength);
    List<Comment> partComments = comments.subList(commentQuarterLength * 2, commentLength);
    for (User user : partUsers) {
      for (Post post : partPosts) {
        voteService.createPostVote(new PostVote(post, user, 1));
      }
      for (Comment comment : partComments) {
        voteService.createCommentVote(new CommentVote(comment, user, 1));
      }
    }
  }

  private String genTitle() {
    return RandomGenerator.genMostCharacters(20);
  }

  private String genLink() {
    return "http://" + RandomGenerator.genLCCharStr(3) + "." + RandomGenerator.genLCCharStr(
        6) + "." + RandomGenerator.genLCCharStr(3) + "/" + RandomGenerator.genLCCharStr(6);
  }

  private String genText() {
    return RandomGenerator.genMostCharacters(200);
  }

  private int genInt(int max) {
    return RandomGenerator.genInt(max);
  }

}
