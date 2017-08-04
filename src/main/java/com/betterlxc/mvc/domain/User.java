package com.betterlxc.mvc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by LXC on 2017/5/10.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private Long id;

  private String name;

  private Integer age;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime dateCreated;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime lastUpdated;
}
