package com.yyqian.imagine.dto.validator;

import com.yyqian.imagine.dto.PostCreateForm;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created on 1/8/16.
 *
 * @author Yinyin Qian
 */
@Component
public class PostCreateFormValidator implements Validator {

  private String[] schemes = {"http", "https"};
  private UrlValidator urlValidator = new UrlValidator(schemes);

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.equals(PostCreateForm.class);
  }

  @Override
  public void validate(Object target, Errors errors) {
    PostCreateForm form = (PostCreateForm)target;
    boolean urlIsEmpty = false;
    if (form.getUrl() == null || form.getUrl().isEmpty()) {
      urlIsEmpty = true;
    } else {
      String url = form.getUrl();
      if (!(url.startsWith("http://") || url.startsWith("https://"))) {
        url = "http://" + url;
      }
      if (!urlValidator.isValid(url)) {
        errors.reject("url.invalid", "URL is invalid");
      }
    }
    boolean textIsEmpty = false;
    if (form.getText() == null || form.getText().isEmpty()) {
      textIsEmpty = true;
    }
    if (urlIsEmpty && textIsEmpty) {
      errors.reject("urltext.empty", "Both url and text are empty");
    }
  }

}
