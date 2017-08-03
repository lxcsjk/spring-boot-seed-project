package com.betterlxc.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by GuoTianxiang on 2016/10/22.
 */
@Slf4j
public class Interceptors {

  public static Interceptor httpHeader(String name, String value) {
    return chain -> {
      Request request = chain.request();
      Headers headers = withDefault(
          request.headers(),
          ImmutableMap.of(name, value));

      request = request
          .newBuilder()
          .headers(headers)
          .build();

      return chain.proceed(request);
    };
  }

  private static Headers withDefault(Headers oldHeaders,
                                     Map<String, String> defaultValues) {
    Set<String> names = oldHeaders.names();
    Headers.Builder builder = oldHeaders.newBuilder();

    defaultValues.forEach((name, value) -> {
      if (!names.contains(name)) {
        builder.set(name, value);
      }
    });

    return builder.build();
  }

  public static Response errorLog(Interceptor.Chain chain) throws IOException {
    Request request = chain.request();
    Response response = chain.proceed(request);

    if (response.isSuccessful()) {
      return response;
    }

    String body = response.body().string();
    log.warn("ERROR RESPONSE: {}", body);

    String contentType = MoreObjects.firstNonNull(
        response.header("Content-Type"), "application/json");
    return response.newBuilder()
        .body(ResponseBody.create(MediaType.parse(contentType), body))
        .build();
  }

}
