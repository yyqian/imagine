package com.yyqian.imagine.repository;

import com.yyqian.imagine.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by yyqian on 12/9/15.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findOneByUsername(String username);
}
