package com.yyqian.imagine.service;

import java.util.Map;

/**
 * Created on 12/30/15.
 *
 * @author Yinyin Qian
 */
public interface MailService {

  void sendHtml(String to, String subject, String templateName, Map<String, Object> model);

  void sendRestPasswordEmail(String mailTo, String url);

}
