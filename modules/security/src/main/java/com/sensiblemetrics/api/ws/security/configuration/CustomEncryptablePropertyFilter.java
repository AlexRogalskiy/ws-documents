package com.sensiblemetrics.api.ws.security.configuration;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyFilter;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;

class CustomEncryptablePropertyFilter implements EncryptablePropertyFilter {
    private final String prefix;

    CustomEncryptablePropertyFilter(final String prefix) {
        Assert.notNull(prefix, "Property prefix should not be null");
        this.prefix = prefix;
    }

    public boolean shouldInclude(final PropertySource<?> source, final String name) {
        return name.startsWith(this.prefix);
    }
}
