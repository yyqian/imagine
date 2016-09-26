package com.yyqian.imagine.controller;

import com.yyqian.imagine.dto.PasswordRestForm;
import com.yyqian.imagine.service.SecurityService;
import com.yyqian.imagine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

import static com.yyqian.imagine.constant.UriConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by yyqian on 12/4/15.
 * Control all pages
 */
@Controller
public class PageController {

  private final SecurityService securityService;
  private final UserService userService;

  @Autowired
  public PageController(SecurityService securityService, UserService userService) {
    this.securityService = securityService;
    this.userService = userService;
  }

  @RequestMapping(value = "/", method = GET)
  public String index() {
    return "redirect:/post";
  }

  @RequestMapping(value = "/about", method = GET)
  public String about(Model model) {
    model.addAttribute("isLoggedIn", securityService.isLoggedIn());
    model.addAttribute("username", securityService.getUsername());
    return "about";
  }

  @RequestMapping(value = "/login", method = GET)
  public String login(@RequestParam Optional<String> error, Model model) {
    model.addAttribute("error", error);
    return "login";
  }

  @RequestMapping(value = "/admin", method = GET)
  public String admin(Model model) {
    model.addAttribute("isLoggedIn", securityService.isLoggedIn());
    model.addAttribute("username", securityService.getUsername());
    return "admin";
  }

  @RequestMapping(value = "/forgot", method = GET)
  public String forgot() {
    return "forgot";
  }

  @RequestMapping(value = RESET_PASSWORD_URI, method = GET)
  public String resetSelfPassword(Model model) {
    model.addAttribute("isLoggedIn", securityService.isLoggedIn());
    model.addAttribute("username", securityService.getUsername());
    return "resetpw";
  }

  @RequestMapping(value = RESET_PASSWORD_URI_PREFIX + "{token}", method = GET)
  public String resetpw(@PathVariable("token") String token, Model model) {
    userService.authUserByToken(token);
    return "resetpw";
  }

  @RequestMapping(value = "/forgot", method = POST)
  public String forgotHandler(@RequestParam(value = "username", required = true) String username) {
    userService.sendRestPasswordEmail(username);
    return "password-reset-sent";
  }

  @RequestMapping(value = "/resetpw", method = POST)
  public String resetpwHandler(@Valid PasswordRestForm form) {
    userService.resetPassword(form);
    return "redirect:/user/self";
  }

}
