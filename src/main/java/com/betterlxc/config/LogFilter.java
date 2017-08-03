package com.betterlxc.config;

import com.betterlxc.utils.HttpRequestUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LogFilter implements Filter {

  private static Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("Service Start......");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
    long start = 0;
    long end = 0;
    HttpServletRequest request = HttpServletRequest.class.cast(servletRequest);
    HttpServletResponse response = HttpServletResponse.class.cast(servletResponse);
    String url = HttpRequestUtils.getPath(request);
    String IP = HttpRequestUtils.getIpAddr(request);
    try {
      start = System.currentTimeMillis();
      chain.doFilter(request, response);
      end = System.currentTimeMillis();
    } finally {
      log.info("[IP : {}] Method : {}: Status : {},  time : {}ms, url : {} , params :{}", IP, request.getMethod(), response.getStatus(), (end - start), url,
          GSON.toJson(request.getParameterMap()));
    }
  }

  @Override
  public void destroy() {
    log.info("Service Stop......");
  }
}
