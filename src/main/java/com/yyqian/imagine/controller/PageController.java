package com.yyqian.imagine.controller;

import com.yyqian.imagine.constant.UriConstant;
import com.yyqian.imagine.dto.PasswordRestForm;
import com.yyqian.imagine.service.UserService;
import com.yyqian.imagine.utility.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static com.yyqian.imagine.constant.UriConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by yyqian on 12/4/15.
 * Control all pages
 */
@Controller
public class PageController {

  private final UserService userService;
  private final ControllerUtil controllerUtil;

  @Autowired
  public PageController(UserService userService, ControllerUtil controllerUtil) {
    this.userService = userService;
    this.controllerUtil = controllerUtil;
  }

  @RequestMapping(value = "/", method = GET)
  public String index() {
    return "redirect:" + UriConstant.POST;
  }

  @RequestMapping(value = ABOUT, method = GET)
  public String about(Model model) {
    controllerUtil.addLoginInfo(model);
    return "about";
  }

  @RequestMapping(value = LOGIN, method = GET)
  public String login(@RequestParam(required = false) String error, Model model) {
    if (error != null) {
      model.addAttribute("error", error);
    }
    return "login";
  }

  @RequestMapping(value = FORGOT, method = GET)
  public String forgot() {
    return "forgot";
  }

  @RequestMapping(value = RESETPW, method = GET)
  public String resetSelfPassword(Model model) {
    controllerUtil.addLoginInfo(model);
    return "resetpw";
  }

  @RequestMapping(value = RESETPW + "/{token}", method = GET)
  public String resetpw(@PathVariable("token") String token) {
    userService.authUserByToken(token);
    return "resetpw";
  }

  @RequestMapping(value = FORGOT, method = POST)
  public String forgotHandler(@RequestParam(value = "username") String username) {
    userService.sendRestPasswordEmail(username);
    return "password-reset-sent";
  }

  @RequestMapping(value = RESETPW, method = POST)
  public String resetpwHandler(@Valid PasswordRestForm form) {
    userService.resetPassword(form);
    return "redirect:" + UriConstant.USER_SELF;
  }

}
