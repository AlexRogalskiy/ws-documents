package com.sensiblemetrics.api.ws.document.uploader.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

@UtilityClass
public class ServiceUtils {

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
