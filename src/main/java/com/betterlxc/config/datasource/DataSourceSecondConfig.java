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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Created by LXC on 2017/5/10.
 */
@Configuration
@MapperScan(basePackages = DataSourceSecondConfig.PACKAGE, sqlSessionTemplateRef = "secondSqlSessionTemplate")
public class DataSourceSecondConfig {

  static final String PACKAGE = "com.betterlxc.mvc.mapper.second";

  @Bean(name = "secondDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.second")
  public HikariDataSource secondDataSourceDataSource() {
    return new HikariDataSource();
  }

  @Bean(name = "secondTransactionManager")
  public DataSourceTransactionManager secondDataSourceTransactionManager() {
    return new DataSourceTransactionManager(secondDataSourceDataSource());
  }

  @Bean(name = "secondSqlSessionFactory")
  public SqlSessionFactory secondDataSourceSqlSessionFactory(
      @Qualifier("secondDataSource") HikariDataSource secondDataSourceDataSource) throws Exception {
    final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(secondDataSourceDataSource);
    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/second/*.xml"));
    return bean.getObject();
  }

  @Bean(name = "secondSqlSessionTemplate")
  public SqlSessionTemplate secondSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
