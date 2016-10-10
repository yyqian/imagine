package com.yyqian.imagine.service;

import com.yyqian.imagine.dto.PostCreateForm;
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
public interface PostService {
  Optional<Post> getPostById(long id);

  Page<Post> getPostListByPage(Pageable pageable);

  Collection<Post> getPostListBySite(String site);

  Collection<Post> getPostListByCurrentUser();

  Post create(Post post);

  Post create(PostCreateForm form);

  Post update(Post post);

}
