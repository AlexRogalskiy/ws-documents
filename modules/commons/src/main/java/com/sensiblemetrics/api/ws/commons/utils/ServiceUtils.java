package com.sensiblemetrics.api.ws.commons.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

@UtilityClass
public class ServiceUtils {

    /**
     * Returns {@link Resource} by input {@link String} pattern
     *
     * @param pattern initial input {@link String} to fetch resources by
     * @return new {@link Resource} instance by first pattern matching
     */
    @NonNull
    public static Resource findInClasspath(final String pattern) {
        final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            final Resource[] resources = resolver.getResources(pattern);
            return Arrays.stream(resources).findFirst().orElseThrow(FileNotFoundException::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
