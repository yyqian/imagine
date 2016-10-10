package com.yyqian.imagine.utility;

import com.yyqian.imagine.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * Created on 10/10/2016.
 *
 * @author Yinyin Qian
 */
@Component
public class ControllerUtil {
  @Autowired
  private SecurityService securityService;

  public void addLoginInfo(Model model) {
    boolean isLoggedIn = securityService.isLoggedIn();
    model.addAttribute("isLoggedIn", isLoggedIn);
    if (isLoggedIn) {
      model.addAttribute("username", securityService.getUsername());
    }
  }
}
