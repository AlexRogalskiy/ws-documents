package com.sensiblemetrics.api.ws.caching.configuration;

import com.sensiblemetrics.api.ws.caching.property.WsCachingProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

@Configuration
@EnableConfigurationProperties(WsCachingProperty.class)
@ConditionalOnProperty(
    prefix = WsCachingProperty.PROPERTY_PREFIX,
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Caching configuration")
public abstract class WsCachingConfiguration {
  /** Default caching bean naming convention */
  public static final String CONCURRENT_CACHE_MANAGER_CUSTOMIZER_BEAN_NAME =
      "concurrentCacheManagerCustomizer";

  @Bean(CONCURRENT_CACHE_MANAGER_CUSTOMIZER_BEAN_NAME)
  @ConditionalOnMissingBean(name = CONCURRENT_CACHE_MANAGER_CUSTOMIZER_BEAN_NAME)
  @Description("Concurrent cache manager customizer bean")
  public <T extends ConcurrentMapCacheManager>
      CacheManagerCustomizer<T> concurrentCacheManagerCustomizer() {
    return cacheManager -> cacheManager.setAllowNullValues(false);
  }
}
