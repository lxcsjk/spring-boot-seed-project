package com.betterlxc.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by LXC on 2017/5/10.
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
