package com.betterlxc.mvc.controller;

import com.betterlxc.mvc.domain.User;
import com.betterlxc.mvc.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by LXC on 2017/5/10.
 */
@Slf4j
@RestController
@RequestMapping("/seed")
public class TestController {

  private final TestService testService;

  @Autowired
  public TestController(TestService testService) {
    this.testService = testService;
  }

  @GetMapping("test")
  public Map<String, List<User>> selectAll() {
    return testService.selectAll();
  }

  @GetMapping("test1")
  public List<User> selectTest1All() {
    return testService.selectTest1All();
  }

  @GetMapping("test2")
  public List<User> selectTest2All() {
    return testService.selectTest2All();
  }
}
