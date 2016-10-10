package com.yyqian.imagine.dto.validator;

import com.yyqian.imagine.dto.UserCreateForm;
import com.yyqian.imagine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by yyqian on 12/15/15.
 */
@Component
public class UserCreateFormValidator implements Validator {

  private static final int MINIMUM_USERNAME_LENGTH = 3;
  private static final int MINIMUM_PASSWORD_LENGTH = 6;
  private final UserService userService;

  @Autowired
  public UserCreateFormValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.equals(UserCreateForm.class);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordRepeated", "field.required");
    UserCreateForm form = (UserCreateForm)target;
    validatePasswords(errors, form);
    validateUsername(errors, form);
  }

  private void validatePasswords(Errors errors, UserCreateForm form) {
    if (form.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
      errors.rejectValue("password", "field.min.length",
                         new Object[] {Integer.valueOf(MINIMUM_PASSWORD_LENGTH)},
                         "password should be at least " + MINIMUM_PASSWORD_LENGTH + " characters");
    }
    if (!form.getPassword().equals(form.getPasswordRepeated())) {
      errors.reject("password.no_match", "Passwords do not match");
    }
  }

  private void validateUsername(Errors errors, UserCreateForm form) {
    String username = form.getUsername();
    if (username.trim().length() < MINIMUM_USERNAME_LENGTH) {
      errors.rejectValue("username", "field.min.length",
                         new Object[] {Integer.valueOf(MINIMUM_USERNAME_LENGTH)},
                         "username should be at least " + MINIMUM_USERNAME_LENGTH + " characters");
    }
    if (userService.getUserByUsername(username).isPresent()) {
      errors.reject("username.exists", "User with this username already exists");
    }
  }
}
