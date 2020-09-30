package com.sensiblemetrics.api.ws.security.handler;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import java.util.Optional;

public class CustomEncryptablePropertyResolver implements EncryptablePropertyResolver {
    private static final String DEFAULT_PREFIX = "{cipher}";

    private final String prefix;
    private final PooledPBEStringEncryptor encryptor;

    public CustomEncryptablePropertyResolver(final String prefix,
                                             final SimpleStringPBEConfig config) {
        this.prefix = Optional.ofNullable(prefix).orElse(DEFAULT_PREFIX);
        this.encryptor = new PooledPBEStringEncryptor();
        this.encryptor.setConfig(config);
    }

    @Override
    public String resolvePropertyValue(final String value) {
        return Optional.ofNullable(value)
                .filter(v -> v.startsWith(this.prefix))
                .map(v -> this.encryptor.decrypt(v.substring(this.prefix.length())))
                .orElse(value);
    }
}
