package com.betterlxc.mvc.controller;

import com.betterlxc.mvc.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LXC on 2017/5/10.
 */
@Slf4j
@RestController
@RequestMapping("/hot")
public class TestController {

  private final TestService testService;

  @Autowired
  public TestController(TestService testService) {
    this.testService = testService;
  }
}
