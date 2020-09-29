package com.sensiblemetrics.api.ws.security.handler;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyFilter;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;

public class DelegatedEncryptablePropertyFilter implements EncryptablePropertyFilter {
  private final String prefix;

  public DelegatedEncryptablePropertyFilter(final String prefix) {
    Assert.notNull(prefix, "Property prefix should not be null");
    this.prefix = prefix;
  }

  public boolean shouldInclude(final PropertySource<?> source, final String name) {
    return name.startsWith(this.prefix);
  }
}
