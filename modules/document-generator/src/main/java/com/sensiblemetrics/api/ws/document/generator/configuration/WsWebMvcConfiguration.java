package com.sensiblemetrics.api.ws.document.generator.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.time.Duration;
import java.util.Locale;
import java.util.Optional;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
@Description("SensibleMetrics Web Service Document Generator WebMVC configuration")
public class WsWebMvcConfiguration implements WebMvcConfigurer {
    private final AsyncTaskExecutor asyncTaskExecutor;
    private final CallableProcessingInterceptor callableProcessingInterceptor;
    private final WebMvcProperties mvcProperties;

    @Override
    public void configureAsyncSupport(@NonNull final AsyncSupportConfigurer configurer) {
        this.setAsyncTimeout(configurer)
                .setTaskExecutor(this.asyncTaskExecutor)
                .registerCallableInterceptors(this.callableProcessingInterceptor);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(this.localeChangeInterceptor());
    }

    @Bean
    @Description("Default locale resolver bean")
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return sessionLocaleResolver;
    }

    @Bean
    @Description("Default locale change interceptor bean")
    public LocaleChangeInterceptor localeChangeInterceptor() {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    private AsyncSupportConfigurer setAsyncTimeout(final AsyncSupportConfigurer configurer) {
        final Duration timeout = this.mvcProperties.getAsync().getRequestTimeout();
        Optional.ofNullable(timeout).map(Duration::toMillis).ifPresent(configurer::setDefaultTimeout);
        return configurer;
    }
}