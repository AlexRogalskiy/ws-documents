package com.sensiblemetrics.api.ws.executor.configuration;

import com.sensiblemetrics.api.ws.executor.handler.DelegatedAsyncUncaughtExceptionHandler;
import com.sensiblemetrics.api.ws.executor.handler.DelegatedRejectedExecutionHandler;
import com.sensiblemetrics.api.ws.executor.handler.DelegatedTimeoutCallableProcessingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;

import java.util.concurrent.RejectedExecutionHandler;

@Configuration
@RequiredArgsConstructor
@Import(TaskExecutionAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.task.execution", value = "enabled", havingValue = "true", matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Executor configuration")
public abstract class WsExecutorConfiguration {
    /**
     * Default thread pool task executor bean naming convention
     */
    public static final String TASK_EXECUTOR_BUILDER_BEAN_NAME = "taskExecutorBuilder";

    @Configuration
    @RequiredArgsConstructor
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("SensibleMetrics Web Service Task Executor configuration")
    public static class WsTaskExecutorConfiguration implements AsyncConfigurer {
        /**
         * Default async task executor bean naming convention
         */
        public static final String ASYNC_TASK_EXECUTOR_BEAN_NAME = "asyncTaskExecutor";

        private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
        private final AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler;

        /**
         * {@inheritDoc}
         *
         * @see AsyncConfigurer
         */
        @Override
        public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            return this.asyncUncaughtExceptionHandler;
        }

        /**
         * {@inheritDoc}
         *
         * @see AsyncConfigurer
         */
        @Override
        @Bean(ASYNC_TASK_EXECUTOR_BEAN_NAME)
        @Description("Default asynchronous task executor bean")
        public AsyncTaskExecutor getAsyncExecutor() {
            return new TaskExecutorAdapter(this.threadPoolTaskExecutor);
        }
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    @DependsOn(TASK_EXECUTOR_BUILDER_BEAN_NAME)
    @Description("Thread pool task executor configuration bean")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(final TaskExecutorBuilder taskExecutorBuilder,
                                                         final RejectedExecutionHandler rejectedExecutionHandler) {
        final ThreadPoolTaskExecutor taskExecutor = taskExecutorBuilder.build();
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(DelegatedTimeoutCallableProcessingInterceptor.class)
    @Description("Callable processing interceptor configuration bean")
    public CallableProcessingInterceptor callableProcessingInterceptor() {
        return new DelegatedTimeoutCallableProcessingInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(DelegatedRejectedExecutionHandler.class)
    @Description("Rejected execution handler configuration bean")
    public RejectedExecutionHandler rejectedExecutionHandler() {
        return new DelegatedRejectedExecutionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(DelegatedAsyncUncaughtExceptionHandler.class)
    @Description("Default asynchronous uncaught exception handler bean")
    public AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
        return new DelegatedAsyncUncaughtExceptionHandler();
    }
}

