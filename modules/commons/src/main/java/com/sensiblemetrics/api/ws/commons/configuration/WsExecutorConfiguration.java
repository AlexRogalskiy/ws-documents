package com.sensiblemetrics.api.ws.commons.configuration;

import com.sensiblemetrics.api.ws.commons.handler.DefaultAsyncUncaughtExceptionHandler;
import com.sensiblemetrics.api.ws.commons.handler.DefaultRejectedExecutionHandler;
import com.sensiblemetrics.api.ws.commons.handler.DefaultTimeoutCallableProcessingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
@AutoConfigureAfter(TaskExecutionAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.task.execution", value = "enabled", havingValue = "true")
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons WS Executor configuration")
public abstract class WsExecutorConfiguration {
    /**
     * Default thread pool task executor bean naming convention
     */
    public static final String THREAD_POOL_TASK_EXECUTOR_BEAN_NAME = "threadPoolTaskExecutor";

    @Configuration
    @RequiredArgsConstructor
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("SensibleMetrics Commons WS Task Executor configuration")
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

    @Bean(name = THREAD_POOL_TASK_EXECUTOR_BEAN_NAME, destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    @DependsOn("taskExecutorBuilder")
    @Description("Default thread pool task executor configuration bean")
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
    @ConditionalOnClass(DefaultTimeoutCallableProcessingInterceptor.class)
    @Description("Default callable processing interceptor bean")
    public CallableProcessingInterceptor callableProcessingInterceptor() {
        return new DefaultTimeoutCallableProcessingInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(DefaultRejectedExecutionHandler.class)
    @Description("Default rejected execution handler bean")
    public RejectedExecutionHandler rejectedExecutionHandler() {
        return new DefaultRejectedExecutionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(DefaultAsyncUncaughtExceptionHandler.class)
    @Description("Default asynchronous uncaught exception handler bean")
    public AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
        return new DefaultAsyncUncaughtExceptionHandler();
    }
}

