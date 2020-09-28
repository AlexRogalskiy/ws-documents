package com.sensiblemetrics.api.ws.security.configuration;

import com.sensiblemetrics.api.ws.security.handler.CustomEncryptablePropertyDetector;
import com.sensiblemetrics.api.ws.security.handler.CustomEncryptablePropertyFilter;
import com.sensiblemetrics.api.ws.security.handler.CustomEncryptablePropertyResolver;
import com.sensiblemetrics.api.ws.security.property.WsEncryptableProperty;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyFilter;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

@Configuration
@EnableConfigurationProperties(WsEncryptableProperty.class)
@ConditionalOnProperty(prefix = WsEncryptableProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Security Encryptable configuration")
public abstract class WsEncryptableConfiguration {
    /**
     * Default encryptable bean naming conventions
     */
    public static final String JASYPT_STRING_ENCRYPTOR_BEAN_NAME = "jasyptStringEncryptor";
    public static final String JASYPT_STRING_ENCRYPTOR_CONFIGURATION_BEAN_NAME = "jasyptStringEncryptorConfig";
    public static final String ENCRYPTABLE_PROPERTY_DETECTOR_BEAN_NAME = "encryptablePropertyDetector";
    public static final String ENCRYPTABLE_PROPERTY_RESOLVER_BEAN_NAME = "encryptablePropertyResolver";
    public static final String ENCRYPTABLE_PROPERTY_FILTER_BEAN_NAME = "encryptablePropertyFilter";

    @Bean(JASYPT_STRING_ENCRYPTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = JASYPT_STRING_ENCRYPTOR_BEAN_NAME)
    @ConditionalOnBean(SimpleStringPBEConfig.class)
    @Description("Security string encryptor bean")
    public StringEncryptor stringEncryptor(@Qualifier(JASYPT_STRING_ENCRYPTOR_CONFIGURATION_BEAN_NAME) final SimpleStringPBEConfig config) {
        final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }

    @Bean(ENCRYPTABLE_PROPERTY_DETECTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = ENCRYPTABLE_PROPERTY_DETECTOR_BEAN_NAME)
    @ConditionalOnBean(WsEncryptableProperty.class)
    @ConditionalOnClass(CustomEncryptablePropertyDetector.class)
    @Description("Security encryptable property detector bean")
    public EncryptablePropertyDetector encryptablePropertyDetector(final WsEncryptableProperty property) {
        return new CustomEncryptablePropertyDetector(property.getEncryptedPrefix());
    }

    @Bean(ENCRYPTABLE_PROPERTY_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean(name = ENCRYPTABLE_PROPERTY_RESOLVER_BEAN_NAME)
    @ConditionalOnBean({WsEncryptableProperty.class, SimpleStringPBEConfig.class})
    @ConditionalOnClass(CustomEncryptablePropertyResolver.class)
    @Description("Security encryptable property resolver bean")
    public EncryptablePropertyResolver encryptablePropertyResolver(final WsEncryptableProperty property,
                                                                   final SimpleStringPBEConfig config) {
        return new CustomEncryptablePropertyResolver(property.getEncryptedPrefix(), config);
    }

    @Bean(ENCRYPTABLE_PROPERTY_FILTER_BEAN_NAME)
    @ConditionalOnMissingBean(name = ENCRYPTABLE_PROPERTY_FILTER_BEAN_NAME)
    @ConditionalOnBean(WsEncryptableProperty.class)
    @ConditionalOnClass(CustomEncryptablePropertyFilter.class)
    @Description("Security encryptable property filter bean")
    public EncryptablePropertyFilter encryptablePropertyFilter(final WsEncryptableProperty property) {
        return new CustomEncryptablePropertyFilter(property.getEncryptedMarker());
    }

    @Bean(JASYPT_STRING_ENCRYPTOR_CONFIGURATION_BEAN_NAME)
    @ConditionalOnMissingBean(name = JASYPT_STRING_ENCRYPTOR_CONFIGURATION_BEAN_NAME)
    @ConditionalOnBean(WsEncryptableProperty.class)
    @Description("Default String Encryptor configuration bean")
    public SimpleStringPBEConfig stringEncryptorConfig(final WsEncryptableProperty property) {
        final SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPasswordCharArray("password".toCharArray());
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize(1);
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        return config;
    }
}
