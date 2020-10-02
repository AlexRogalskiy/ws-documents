package com.sensiblemetrics.api.ws.validation.configuration;

import com.sensiblemetrics.api.ws.validation.aspect.MethodParamsValidationAspect;
import com.sensiblemetrics.api.ws.validation.model.DefaultValidatorRegistry;
import com.sensiblemetrics.api.ws.validation.model.ValidatorRegistry;
import com.sensiblemetrics.api.ws.validation.property.WebServiceValidationProperty;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.ValidatorFactory;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnProperty(prefix = WebServiceValidationProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WebServiceValidationProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebService Validation configuration")
public abstract class WebServiceValidationConfiguration {

    @Bean
    @ConditionalOnBean(ValidatorRegistry.class)
    @ConditionalOnProperty(prefix = WebServiceValidationProperty.Handlers.METHOD_PARAMS_PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Description("Method params validation aspect bean")
    public MethodParamsValidationAspect methodParamsValidationAspect(final ValidatorRegistry validatorRegistry) {
        return new MethodParamsValidationAspect(validatorRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    @Description("Validator registry configuration bean")
    public ValidatorRegistry validatorRegistry(final ObjectProvider<java.util.List<Validator>> validatorProvider) {
        final DefaultValidatorRegistry registry = new DefaultValidatorRegistry();
        validatorProvider.ifAvailable(validators -> validators.forEach(registry::addValidator));
        return registry;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessageSource.class)
    @ConditionalOnClass(LocalValidatorFactoryBean.class)
    @Description("Validator factory configuration bean")
    public ValidatorFactory validatorFactory(final MessageSource messageSource,
                                             final ParameterNameDiscoverer parameterNameDiscoverer) {
        final LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setValidationMessageSource(messageSource);
        factoryBean.setParameterNameDiscoverer(parameterNameDiscoverer);
        return factoryBean;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(LocalVariableTableParameterNameDiscoverer.class)
    @Description("Parameter name discoverer configuration bean")
    public ParameterNameDiscoverer parameterNameDiscoverer() {
        return new LocalVariableTableParameterNameDiscoverer();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(ValidatorFactory.class)
    @Description("Method validation post processor configuration bean")
    public MethodValidationPostProcessor methodValidationPostProcessor(final ValidatorFactory validatorFactory) {
        final MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidatorFactory(validatorFactory);
        return processor;
    }
}
