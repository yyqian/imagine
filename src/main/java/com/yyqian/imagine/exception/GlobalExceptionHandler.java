package com.yyqian.imagine.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yyqian on 12/7/15.
 * GlobalDefaultExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {

  @ExceptionHandler(value = {BadRequestException.class, ForbiddenException.class,
      InternalServerErrorException.class, NotFoundException.class, UnauthorizedException.class})
  void restExceptionHandler(RuntimeException ex, HttpServletResponse response) throws IOException {
    int statusCode;
    if (ex instanceof BadRequestException) {
      statusCode = HttpServletResponse.SC_BAD_REQUEST;
    } else if (ex instanceof ForbiddenException) {
      statusCode = HttpServletResponse.SC_FORBIDDEN;
    } else if (ex instanceof NotFoundException) {
      statusCode = HttpServletResponse.SC_NOT_FOUND;
    } else if (ex instanceof UnauthorizedException) {
      statusCode = HttpServletResponse.SC_UNAUTHORIZED;
    } else {
      statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
    response.sendError(statusCode);
  }

}
