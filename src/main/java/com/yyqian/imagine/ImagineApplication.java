package com.yyqian.imagine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class ImagineApplication {

  private static final Logger logger = LoggerFactory.getLogger(ImagineApplication.class);

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(ImagineApplication.class, args);
    String[] beanNames = context.getBeanDefinitionNames();
    Arrays.sort(beanNames);
    logger.debug("Beans:\n  " + String.join("\n  ", beanNames));
  }
}
