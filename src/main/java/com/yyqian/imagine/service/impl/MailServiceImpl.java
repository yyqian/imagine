package com.yyqian.imagine.service.impl;

import com.yyqian.imagine.service.MailService;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yyqian on 12/30/15.
 */
@Service
public class MailServiceImpl implements MailService {

  @Value("${app.name}")
  private String APP_NAME;

  @Autowired
  private JavaMailSenderImpl mailSender;
  private VelocityEngine velocityEngine;

  public MailServiceImpl() {
    velocityEngine = new VelocityEngine();
    velocityEngine.init();
  }

  @Override
  public void sendHtml(String to, String subject, String templateName, Map<String, Object> model) {
    if (!EmailValidator.getInstance().isValid(to)) {
      return;
    }
    String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", model);
    MimeMessage msg = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(msg, true);
      helper.setFrom(mailSender.getUsername());
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(emailText, true);
    } catch (MessagingException ex) {
      throw new RuntimeException("MessagingException from MimeMessageHelper");
    }
    mailSender.send(msg);
  }

  @Override
  public void sendRestPasswordEmail(String mailTo, String urlPrefix, String token) {
    String url = urlPrefix + token;
    String subject = String.format("%s Password Recovery", APP_NAME);
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("url", url);
    model.put("name", APP_NAME);
    sendHtml(mailTo, subject, "password-reset-email.vm", model);
  }

}
