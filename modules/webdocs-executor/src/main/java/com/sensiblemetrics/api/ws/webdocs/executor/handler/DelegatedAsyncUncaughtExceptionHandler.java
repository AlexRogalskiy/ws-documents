package com.sensiblemetrics.api.ws.webdocs.executor.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.String.format;

/** {@link AsyncUncaughtExceptionHandler} implementation */
@Slf4j
public class DelegatedAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
  /**
   * {@inheritDoc}
   *
   * @see AsyncUncaughtExceptionHandler
   */
  @Override
  public void handleUncaughtException(
      final Throwable throwable, final Method method, @NonNull final Object... obj) {
    final StringBuilder sb = new StringBuilder();
    sb.append(format("Exception message={%s}", throwable.getMessage()));
    sb.append(format("Method name={%s}", method.getName()));
    Arrays.stream(obj).forEach(param -> sb.append(format("Param={%s}", param)));
    log.info(sb.toString());
  }
}
