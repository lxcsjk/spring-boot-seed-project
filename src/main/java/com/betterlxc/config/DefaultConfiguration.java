package com.betterlxc.config;

import com.betterlxc.client.Interceptors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by GuoTianxiang on 2016/11/12.
 */
@Configuration
public class DefaultConfiguration {

  @Order(1)
  @Bean("mbxcDefaultJackson2ObjectMapperBuilderCustomizer")
  @ConditionalOnClass({ObjectMapper.class, Jackson2ObjectMapperBuilder.class})
  public Jackson2ObjectMapperBuilderCustomizer mbxcDefaultJackson2ObjectMapperBuilderCustomizer() {
    return builder -> {
      JavaTimeModule module = new JavaTimeModule();
      module.addDeserializer(LocalDateTime.class, MyLocalDateTimeDeserializer.INSTANCE);

      builder.modules(module);
      builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      builder.featuresToDisable(SerializationFeature.WRITE_NULL_MAP_VALUES);
      builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
      builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    };
  }

  @Bean
  @ConditionalOnClass(ObjectMapper.class)
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
    return new MappingJackson2HttpMessageConverter(objectMapper);
  }

  @Bean
  @ConditionalOnClass({OkHttpClient.class, HttpLoggingInterceptor.class})
  @ConditionalOnMissingBean(name = "httpLoggingInterceptor")
  public HttpLoggingInterceptor httpLoggingInterceptor(@Value("${http.level:BASIC}") String level) {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.valueOf(level.toUpperCase()));
    return interceptor;
  }

  @Bean
  @ConditionalOnClass({OkHttpClient.class, HttpLoggingInterceptor.class})
  @ConditionalOnMissingBean(name = "okHttpClient")
  public OkHttpClient okHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
    return new OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.MINUTES)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(Interceptors.httpHeader("X-BETTER-LXC", "YoRuo"))
        .addInterceptor(Interceptors::errorLog)
        .build();
  }

  @Bean
  @ConditionalOnMissingBean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean bean = new FilterRegistrationBean();
    bean.setFilter(new LogFilter());
    bean.setOrder(Ordered.LOWEST_PRECEDENCE);
    return bean;
  }
}
