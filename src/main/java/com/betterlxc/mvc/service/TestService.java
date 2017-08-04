package com.betterlxc.mvc.service;

import com.betterlxc.mvc.domain.User;
import com.betterlxc.mvc.mapper.primary.PrimaryMapper;
import com.betterlxc.mvc.mapper.second.SecondMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

  public List<User> selectTest1All() {
    return primaryMapper.selectAll();
  }

  public List<User> selectTest2All() {
    return secondMapper.selectAll();
  }

  public Map<String, List<User>> selectAll() {
    Map<String, List<User>> map = Maps.newHashMap();
    map.put("数据源1", primaryMapper.selectAll());
    map.put("数据源2", secondMapper.selectAll());
    return map;
  }
}
