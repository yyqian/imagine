package com.yyqian.imagine.repository;

import com.yyqian.imagine.po.Post;
import com.yyqian.imagine.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by yyqian on 12/5/15.
 * In a typical Java application, you’d expect to write a class that implements PostRepository.
 * But that’s what makes Spring Data JPA so powerful: You don’t have to write an implementation of
 * the repository interface. Spring Data JPA creates an implementation on the fly when you run
 * the application.
 * In summary:
 * 1. Interface-based programming entity
 * 2. No implementation required
 * 3. Queries derived from method names
 * CrudRepository: exposed CRUD methods
 * PagingAndSortingRepository: exposed CRUD methods and paging ones
 * JpaRepository: findAll() will return List<T>, the transaction timeout for save() is customized
 * to 10 secs.
 *
 * Repository annotation is optional, just for the sake of Intellij IDEA. IDEA will complain without it.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  Collection<Post> findBySite(String site);
  Collection<Post> findByUser(User user);
}
