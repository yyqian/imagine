package com.yyqian.imagine.service.impl;

import com.yyqian.imagine.dto.PostCreateForm;
import com.yyqian.imagine.po.Post;
import com.yyqian.imagine.po.User;
import com.yyqian.imagine.repository.PostRepository;
import com.yyqian.imagine.service.PostService;
import com.yyqian.imagine.service.SecurityService;
import com.yyqian.imagine.utility.StringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final SecurityService securityService;

  @Autowired
  public PostServiceImpl(PostRepository postRepository, SecurityService securityService) {
    this.postRepository = postRepository;
    this.securityService = securityService;
  }

  @Override
  public Optional<Post> getPostById(long id) {
    return Optional.ofNullable(postRepository.findOne(id));
  }

  @Override
  public Page<Post> getPostListByPage(Pageable pageable) {
    return postRepository.findAll(pageable);
  }

  @Override
  public Collection<Post> getPostListBySite(String site) {
    return postRepository.findBySite(site);
  }

  @Override
  public Collection<Post> getPostListByCurrentUser() {
    if (!securityService.isLoggedIn()) {
      return Collections.emptyList();
    }
    return postRepository.findByUser(securityService.getUser());
  }

  @Transactional
  @Override
  public Post create(Post post) {
    return postRepository.save(post);
  }

  @Transactional
  @Override
  public Post update(Post post) {
    return postRepository.save(post);
  }

  @Transactional
  @Override
  public Post create(PostCreateForm form) {
    User user = securityService.getUser();
    Post post;
    String url = form.getUrl();
    String content;
    Post.Kind kind;
    if (url == null || url.isEmpty()) {
      kind = Post.Kind.PLAIN;
      content = form.getText();
    } else {
      kind = Post.Kind.LINK;
      if (url.startsWith("http://") || url.startsWith("https://")) {
        content = url;
      } else {
        content = "http://" + url;
      }
    }
    post = new Post(form.getTitle(), content, user, kind);
    if (kind == Post.Kind.LINK) {
      post.setSite(StringUtility.getSite(content));
    }
    return postRepository.save(post);
  }

}
