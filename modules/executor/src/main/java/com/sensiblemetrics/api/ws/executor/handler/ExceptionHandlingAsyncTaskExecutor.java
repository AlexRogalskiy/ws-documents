package com.sensiblemetrics.api.ws.executor.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.lang.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * {@link AsyncTaskExecutor} implementation
 */
@Slf4j
@Getter(AccessLevel.PRIVATE)
@AllArgsConstructor
public class ExceptionHandlingAsyncTaskExecutor implements AsyncTaskExecutor, InitializingBean, DisposableBean {

    public static final String EXCEPTION_MESSAGE = "Caught async exception";

    private final AsyncTaskExecutor executor;

    @Override
    public void execute(@NonNull final Runnable task) {
        this.getExecutor().execute(createWrappedRunnable(task));
    }

    @Override
    public void execute(@NonNull final Runnable task,
                        final long startTimeout) {
        this.executor.execute(this.createWrappedRunnable(task), startTimeout);
    }

    private <T> Callable<T> createCallable(final Callable<T> task) {
        return () -> {
            try {
                return task.call();
            } catch (Exception e) {
                handle(e);
                throw e;
            }
        };
    }

    private Runnable createWrappedRunnable(final Runnable task) {
        return () -> {
            try {
                task.run();
            } catch (Exception e) {
                handle(e);
            }
        };
    }

    protected void handle(final Exception e) {
        log.error(EXCEPTION_MESSAGE, e);
    }

    @NonNull
    @Override
    public Future<?> submit(@NonNull final Runnable task) {
        return this.getExecutor().submit(this.createWrappedRunnable(task));
    }

    @NonNull
    @Override
    public <T> Future<T> submit(@NonNull final Callable<T> task) {
        return this.getExecutor().submit(this.createCallable(task));
    }

    @Override
    public void destroy() throws Exception {
        if (this.executor instanceof DisposableBean) {
            final DisposableBean bean = (DisposableBean) this.executor;
            bean.destroy();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.executor instanceof InitializingBean) {
            final InitializingBean bean = (InitializingBean) this.executor;
            bean.afterPropertiesSet();
        }
    }
}
