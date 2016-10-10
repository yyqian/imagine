package com.yyqian.imagine.dto.validator;

import com.yyqian.imagine.dto.UserUpdateForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by yyqian on 1/8/16.
 */
@Component
public class UserUpdateFormValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.equals(UserUpdateForm.class);
  }

  @Override
  public void validate(Object target, Errors errors) {
    UserUpdateForm form = (UserUpdateForm)target;
    if (form.getEmail() != null && !form.getEmail().isEmpty()) {
      if (!EmailValidator.getInstance().isValid(form.getEmail())) {
        errors.reject("email.invalid", "The email address is invalid");
      }
    }
  }
}
