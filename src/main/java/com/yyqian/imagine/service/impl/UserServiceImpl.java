package com.yyqian.imagine.service.impl;

import com.yyqian.imagine.constant.UriConstant;
import com.yyqian.imagine.dto.PasswordRestForm;
import com.yyqian.imagine.dto.UserCreateForm;
import com.yyqian.imagine.dto.UserUpdateForm;
import com.yyqian.imagine.exception.BadRequestException;
import com.yyqian.imagine.po.User;
import com.yyqian.imagine.repository.UserRepository;
import com.yyqian.imagine.service.MailService;
import com.yyqian.imagine.service.SecurityService;
import com.yyqian.imagine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SecurityService securityService;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;
  @Autowired
  private MailService mailService;
  @Autowired
  private UserDetailsService userDetailsService;

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  private final String REDIS_MAIL_TOKEN_PREFIX = "mail:token:";
  private final long TOKEN_TIMEOUT = 1L;
  private final TimeUnit TOKEN_TIMEOUT_UNIT = TimeUnit.HOURS;

  @Value("${app.host}")
  private String SITE_URL;

  @Override
  public Optional<User> getUserById(long id) {
    return Optional.ofNullable(userRepository.findOne(id));
  }

  @Override
  public Optional<User> getUserByUsername(String username) {
    return userRepository.findOneByUsername(username);
  }

  @Override
  public Collection<User> getAllUsers() {
    return userRepository.findAll(new Sort(Sort.Direction.DESC, "point"));
  }

  @Override
  public User create(UserCreateForm form) {
    User user = new User();
    user.setUsername(form.getUsername());
    user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
    user.setRole(User.Role.USER);
    return userRepository.save(user);
  }

  @Transactional
  @Override
  public User update(UserUpdateForm form) {
    User user = userRepository.findOneByUsername(securityService.getUsername()).orElseThrow(
        () -> new NoSuchElementException("User not found."));
    user.setAbout(form.getAbout());
    user.setEmail(form.getEmail());
    return userRepository.save(user);
  }

  @Transactional
  @Override
  public int increasePointByUserId(Long id) {
    User user = userRepository.findOne(id);
    int point = user.getPoint() + 1;
    user.setPoint(point);
    userRepository.save(user);
    return point;
  }

  @Transactional
  @Override
  public User resetPassword(PasswordRestForm form) {
    User user = userRepository.findOneByUsername(securityService.getUsername()).orElseThrow(
        () -> new NoSuchElementException("User not found."));
    user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public void sendRestPasswordEmail(String username) {
    Optional<User> optUser = getUserByUsername(username);
    if (!optUser.isPresent()) {
      throw new BadRequestException("User Not Found");
    }
    String email = optUser.get().getEmail();
    if (email == null || email.isEmpty()) {
      throw new BadRequestException("No Email for this user");
    }
    String token = genToken();
    stringRedisTemplate.opsForValue().set(REDIS_MAIL_TOKEN_PREFIX + token, username, TOKEN_TIMEOUT,
                                          TOKEN_TIMEOUT_UNIT);
    mailService.sendRestPasswordEmail(optUser.get().getEmail(),
                                      SITE_URL + UriConstant.RESETPW + "/" + token);
  }

  @Override
  public void authUserByToken(String token) {
    Optional<String> optUsername = getValueFromRedis(token);
    if (!optUsername.isPresent()) {
      throw new BadRequestException("Token Not Found");
    }
    String username = optUsername.get();
    Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                                                                  userDetailsService
                                                                      .loadUserByUsername(
                                                                      username).getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
    stringRedisTemplate.delete(REDIS_MAIL_TOKEN_PREFIX + token);
  }

  public Optional<String> getValueFromRedis(String token) {
    return Optional.ofNullable(
        stringRedisTemplate.opsForValue().get(REDIS_MAIL_TOKEN_PREFIX + token));
  }

  private String genToken() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
