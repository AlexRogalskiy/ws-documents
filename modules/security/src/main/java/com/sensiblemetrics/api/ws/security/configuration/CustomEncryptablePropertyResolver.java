package com.sensiblemetrics.api.ws.security.configuration;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import java.util.Optional;

class CustomEncryptablePropertyResolver implements EncryptablePropertyResolver {
    private final PooledPBEStringEncryptor encryptor;

    CustomEncryptablePropertyResolver(final SimpleStringPBEConfig config) {
        this.encryptor = new PooledPBEStringEncryptor();
        this.encryptor.setConfig(config);
    }

    @Override
    public String resolvePropertyValue(final String value) {
        return Optional.ofNullable(value)
                .filter(v -> v.startsWith("{cipher}"))
                .map(v -> this.encryptor.decrypt(v.substring("{cipher}".length())))
                .orElse(value);
    }
}
