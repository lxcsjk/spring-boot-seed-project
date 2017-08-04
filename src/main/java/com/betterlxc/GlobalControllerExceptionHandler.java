package com.betterlxc;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * Created by LXC on 2017/5/10.
 */
@Slf4j
@ControllerAdvice(basePackages = "com.betterlxc.mvc.controller")
public class GlobalControllerExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({SeedException.class})
  public Map<String, String> handleException(Throwable e) {
    log.warn("", e);

    return ImmutableMap.of("msg",
        String.valueOf(Throwables.getRootCause(e).getMessage()));
  }
}
