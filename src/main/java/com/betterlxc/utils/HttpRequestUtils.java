package com.betterlxc.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtils {

  public static String getIpAddr(HttpServletRequest request) {
    String ipFromNginx = getHeader(request, "X-Real-IP");
    return StringUtils.isEmpty(ipFromNginx) ? request.getRemoteAddr() : ipFromNginx;
  }

  public static String getHeader(HttpServletRequest request, String headName) {
    String value = request.getHeader(headName);
    return !StringUtils.isBlank(value) && !"unknown".equalsIgnoreCase(value) ? value : "";
  }

  public static String getPath(HttpServletRequest request) {
    String request_url = request.getRequestURI();
    String contextPath = request.getContextPath();
    String url = request_url.substring(contextPath.length());
    if (StringUtils.equalsAny(url, "", "/")) {
      return "/index";
    }
    return url;
  }

  public static String getCookie(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) return null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(cookieName)) {
        return cookie.getValue();
      }
    }
    return null;
  }

}
