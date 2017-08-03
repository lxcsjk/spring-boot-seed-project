package com.betterlxc.mvc.service;

import com.betterlxc.mvc.mapper.primary.PrimaryMapper;
import com.betterlxc.mvc.mapper.second.SecondMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LXC on 2017/5/10.
 */
@Slf4j
@Service
public class TestService {

  private final PrimaryMapper primaryMapper;

  private final SecondMapper secondMapper;

  @Autowired
  public TestService(PrimaryMapper primaryMapper, SecondMapper secondMapper) {
    this.primaryMapper = primaryMapper;
    this.secondMapper = secondMapper;
  }
}
