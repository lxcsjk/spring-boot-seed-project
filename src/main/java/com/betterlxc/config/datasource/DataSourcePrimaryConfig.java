package com.betterlxc.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Created by LXC on 2017/5/10.
 */
@Configuration
@MapperScan(basePackages = DataSourcePrimaryConfig.PACKAGE, sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class DataSourcePrimaryConfig {

  static final String PACKAGE = "com.betterlxc.mvc.mapper.primary";

  @Bean(name = "primaryDataSource")
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.primary")
  public HikariDataSource primaryDataSourceDataSource() {
    return new HikariDataSource();
  }

  @Bean(name = "primaryTransactionManager")
  @Primary
  public DataSourceTransactionManager primaryDataSourceTransactionManager() {
    return new DataSourceTransactionManager(primaryDataSourceDataSource());
  }

  @Bean(name = "primarySqlSessionFactory")
  @Primary
  public SqlSessionFactory primaryDataSourceSqlSessionFactory(
      @Qualifier("primaryDataSource") HikariDataSource primaryDataSourceDataSource) throws Exception {
    final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(primaryDataSourceDataSource);
    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/primary/*.xml"));
    return bean.getObject();
  }

  @Bean(name = "primarySqlSessionTemplate")
  @Primary
  public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
