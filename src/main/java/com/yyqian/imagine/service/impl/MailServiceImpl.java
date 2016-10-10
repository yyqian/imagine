package com.yyqian.imagine.service.impl;

import com.yyqian.imagine.exception.BadRequestException;
import com.yyqian.imagine.exception.InternalServerErrorException;
import com.yyqian.imagine.service.MailService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 12/30/15.
 *
 * @author Yinyin Qian
 */
@Service
public class MailServiceImpl implements MailService {

  @Value("${app.name}")
  private String APP_NAME;

  @Autowired
  private JavaMailSenderImpl mailSender;

  @Autowired
  private TemplateEngine templateEngine;

  @Override
  public void sendHtml(String to, String subject, String templateName, Map<String, Object> model) {
    if (!EmailValidator.getInstance().isValid(to)) {
      throw new BadRequestException("Invalid email address.");
    }
    final Context ctx = new Context();
    ctx.setVariables(model);
    final String emailText = templateEngine.process(templateName, ctx);
    final MimeMessage msg = mailSender.createMimeMessage();
    try {
      final MimeMessageHelper helper = new MimeMessageHelper(msg, true);
      helper.setFrom(mailSender.getUsername());
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(emailText, true);
    } catch (MessagingException ex) {
      throw new InternalServerErrorException(ex.getMessage());
    }
    mailSender.send(msg);
  }

  @Override
  public void sendRestPasswordEmail(String mailTo, String url) {
    String subject = String.format("%s Password Recovery", APP_NAME);
    Map<String, Object> model = new HashMap<>();
    model.put("url", url);
    model.put("name", APP_NAME);
    sendHtml(mailTo, subject, "password-reset-email", model);
  }

}
