package com.sensiblemetrics.api.ws.logger.configuration;

import com.sensiblemetrics.api.ws.logger.annotation.EnableWsLogInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;

import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.*;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnBean(annotation = EnableWsLogInterceptor.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Logging Interceptor configuration")
public abstract class WsLoggingInterceptorConfiguration {
    /**
     * Default logging bean naming conventions
     */
    public static final String LOGGING_TRACE_INTERCEPTOR_BEAN_NAME = "loggingTraceInterceptor";
    public static final String LOGGING_TRACE_ADVISOR_BEAN_NAME = "loggingTraceAdvisor";

    /**
     * Trace execution pointcut marker
     */
    private static final String TRACE_POINTCUT_EXECUTION_REPOSITORY = "execution(public * org.springframework.data.repository.Repository+.*(..))";
    /**
     * Trace message formats
     */
    private static final String EXCEPTION_MESSAGE = "Exception thrown: "
            + PLACEHOLDER_EXCEPTION;
    private static final String EXIT_METHOD_MESSAGE = "Exiting method: "
            + PLACEHOLDER_METHOD_NAME + " having return value "
            + PLACEHOLDER_RETURN_VALUE + ", execution time: "
            + PLACEHOLDER_INVOCATION_TIME + " ms";
    private static final String ENTER_METHOD_MESSAGE = "Entering method: "
            + PLACEHOLDER_METHOD_NAME + "("
            + PLACEHOLDER_ARGUMENTS + ")";

    @Bean(LOGGING_TRACE_INTERCEPTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = LOGGING_TRACE_INTERCEPTOR_BEAN_NAME)
    @ConditionalOnClass(CustomizableTraceInterceptor.class)
    @Description("Logging trace interceptor configuration bean")
    public CustomizableTraceInterceptor loggingTraceInterceptor() {
        final CustomizableTraceInterceptor interceptor = new CustomizableTraceInterceptor();
        interceptor.setEnterMessage(ENTER_METHOD_MESSAGE);
        interceptor.setExceptionMessage(EXCEPTION_MESSAGE);
        interceptor.setExitMessage(EXIT_METHOD_MESSAGE);
        return interceptor;
    }

    @Bean(LOGGING_TRACE_ADVISOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = LOGGING_TRACE_ADVISOR_BEAN_NAME)
    @ConditionalOnBean(CustomizableTraceInterceptor.class)
    @ConditionalOnClass(DefaultPointcutAdvisor.class)
    @Description("Logging trace advisor configuration bean")
    public Advisor loggingTraceAdvisor(@Qualifier(LOGGING_TRACE_INTERCEPTOR_BEAN_NAME) final CustomizableTraceInterceptor loggingTraceInterceptor) {
        final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(TRACE_POINTCUT_EXECUTION_REPOSITORY);
        return new DefaultPointcutAdvisor(pointcut, loggingTraceInterceptor);
    }
}
