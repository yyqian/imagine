package com.yyqian.imagine.repository;

import com.yyqian.imagine.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 12/9/15.
 *
 * @author Yinyin Qian
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findOneByUsername(String username);
}
