package com.sensiblemetrics.api.ws.webdocs.executor.handler;

import com.sensiblemetrics.api.ws.webdocs.commons.exception.ServiceOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;

/** Default {@link TimeoutCallableProcessingInterceptor} implementation */
@Slf4j
public class DelegatedTimeoutCallableProcessingInterceptor
    extends TimeoutCallableProcessingInterceptor {
  /**
   * {@inheritDoc}
   *
   * @see TimeoutCallableProcessingInterceptor
   */
  @Override
  public <T> Object handleTimeout(final NativeWebRequest request, final Callable<T> task) {
    return Optional.ofNullable(request.getNativeRequest(HttpServletRequest.class))
        .map(
            req -> {
              log.error(
                  "Timeout has been reached before sending response to client, request: {}",
                  req.getRequestURI());
              return this.getTaskProcessor(task).apply(request);
            })
        .orElse(null);
  }

  /**
   * Returns {@link Function} operator by input {@link Callable} task
   *
   * @param task - initial input {@link Callable} task to execute
   * @return {@link Function} operator
   */
  private <T> Function<NativeWebRequest, Object> getTaskProcessor(final Callable<T> task) {
    return request -> {
      try {
        return super.handleTimeout(request, task);
      } catch (Exception e) {
        throw new ServiceOperationException(e);
      }
    };
  }
}
