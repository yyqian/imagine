package com.yyqian.imagine.controller;

import com.yyqian.imagine.constant.UriConstant;
import com.yyqian.imagine.dto.UserCreateForm;
import com.yyqian.imagine.dto.UserUpdateForm;
import com.yyqian.imagine.dto.validator.UserCreateFormValidator;
import com.yyqian.imagine.dto.validator.UserUpdateFormValidator;
import com.yyqian.imagine.exception.InternalServerErrorException;
import com.yyqian.imagine.service.SecurityService;
import com.yyqian.imagine.service.UserService;
import com.yyqian.imagine.utility.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created on 12/15/15.
 *
 * @author Yinyin Qian
 */
@Controller
public class UserController {

  private final UserService userService;
  private final SecurityService securityService;
  private final ControllerUtil controllerUtil;
  private final UserCreateFormValidator userCreateFormValidator;
  private final UserUpdateFormValidator userUpdateFormValidator;

  @Autowired
  public UserController(UserService userService, ControllerUtil controllerUtil,
                        UserCreateFormValidator userCreateFormValidator,
                        UserUpdateFormValidator userUpdateFormValidator,
                        SecurityService securityService) {
    this.userService = userService;
    this.securityService = securityService;
    this.controllerUtil = controllerUtil;
    this.userCreateFormValidator = userCreateFormValidator;
    this.userUpdateFormValidator = userUpdateFormValidator;
  }

  @InitBinder("userCreateForm")
  public void userCreateFormValidatorBinder(WebDataBinder binder) {
    binder.addValidators(userCreateFormValidator);
  }

  @InitBinder("userUpdateForm")
  public void userUpdateFormValidatorBinder(WebDataBinder binder) {
    binder.addValidators(userUpdateFormValidator);
  }

  @RequestMapping(value = UriConstant.USER_SELF, method = GET)
  public String readSelf(Model model) {
    model.addAttribute("user", userService.getUserByUsername(securityService.getUsername())
                                          .orElseThrow(
                                              () -> new NoSuchElementException("User not found.")));
    controllerUtil.addLoginInfo(model);
    return "self";
  }

  @RequestMapping(value = UriConstant.USER_SELF, method = POST)
  public String updateSelf(@Valid @ModelAttribute("userUpdateForm") UserUpdateForm form,
                           Model model) {
    model.addAttribute("user", userService.update(form));
    controllerUtil.addLoginInfo(model);
    return "self";
  }

  @RequestMapping(value = UriConstant.USER + "/{id:\\d+}", method = GET)
  public String read(@PathVariable("id") Long id, Model model) {
    model.addAttribute("user", userService.getUserById(id)
                                          .orElseThrow(() -> new NoSuchElementException(
                                              String.format("User=%s not found.", id))));
    controllerUtil.addLoginInfo(model);
    return "user";
  }

  @RequestMapping(value = UriConstant.USER, method = POST)
  public String create(@Valid @ModelAttribute("userCreateForm") UserCreateForm userCreateForm,
                       BindingResult bindingResult, HttpServletRequest request) {
    if (bindingResult.hasErrors()) {
      return "redirect:" + UriConstant.LOGIN_ERROR;
    }
    try {
      userService.create(userCreateForm);
    } catch (DataIntegrityViolationException e) {
      bindingResult.reject("username.exists", "Username already exists");
      return "redirect:" + UriConstant.LOGIN_ERROR;
    }
    authenticateUserAndSetSession(userCreateForm, request);
    return "redirect:/";
  }

  @RequestMapping(value = UriConstant.USER, method = GET)
  public String list(Model model) {
    model.addAttribute("users", userService.getAllUsers());
    controllerUtil.addLoginInfo(model);
    return "user-list";
  }

  private void authenticateUserAndSetSession(UserCreateForm form, HttpServletRequest request) {
    try {
      request.login(form.getUsername(), form.getPassword());
    } catch (ServletException e) {
      throw new InternalServerErrorException("Cannot auth the user.");
    }
  }

}
