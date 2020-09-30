package com.sensiblemetrics.api.ws.commons.general.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Test flow implementation
 */
@UtilityClass
public final class TestFlow {

    /**
     * Test flow unit description
     */
    @UtilityClass
    public static class FlowUnit {
        /**
         * Acceptance test type
         */
        public static final String ACCEPTANCE_TEST = "acceptance";
        /**
         * Unit test type
         */
        public static final String UNIT_TEST = "unit";
        /**
         * Functional test type
         */
        public static final String FUNCTIONAL_TEST = "functional";
        /**
         * Integration test type
         */
        public static final String INTEGRATION_TEST = "integration";
        /**
         * Mock test type
         */
        public static final String MOCK_TEST = "mock";
        /**
         * Spring test type
         */
        public static final String SPRING_TEST = "spring";
        /**
         * Flaky test type
         */
        public static final String FLAKY_TEST = "flaky";
        /**
         * Other test type
         */
        public static final String OTHER_TEST = "other";
    }

    /**
     * Test flow {@link Enum} type
     */
    @Getter
    @RequiredArgsConstructor
    public enum FlowType {
        ACCEPTANCE(FlowUnit.ACCEPTANCE_TEST),
        UNIT(FlowUnit.UNIT_TEST),
        FUNCTIONAL(FlowUnit.FUNCTIONAL_TEST),
        INTEGRATION(FlowUnit.INTEGRATION_TEST),
        MOCK(FlowUnit.MOCK_TEST),
        SPRING(FlowUnit.SPRING_TEST),
        FLAKY(FlowUnit.FLAKY_TEST),
        OTHER(FlowUnit.OTHER_TEST);

        /**
         * Default test flow type
         */
        private final String type;

        /**
         * Returns {@link String} representation of current {@link FlowType}
         *
         * @return {@link String} representation
         */
        @Nullable
        public FlowType getValue(final String type) {
            return Arrays.stream(values())
                    .filter(value -> value.getType().equalsIgnoreCase(type))
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * Default {@link Map} collection of {@link FlowType}s
     */
    private static final Map<String, FlowType> params = new LinkedHashMap<>();

    /**
     * Returns {@link FlowType} by input {@link String} value
     *
     * @param value - initial input {@link String} value to fetch by
     * @return {@link FlowType}
     */
    public static FlowType forValue(final String value) {
        return params.get(StringUtils.lowerCase(value));
    }

    /**
     * Determine whether input {@link FlowType} matches the {@code MOCK} test flow
     *
     * @param value - initial input {@link String} value to check
     * @return {@code true} if it matches, {@code false} otherwise
     */
    public static boolean isMockFlow(final String value) {
        return Objects.equals(forValue(value), FlowType.MOCK);
    }

    /**
     * Determine whether input {@link FlowType} matches the {@code SPRING} test flow
     *
     * @param value - initial input {@link String} value to check
     * @return {@code true} if it matches, {@code false} otherwise
     */
    public static boolean isSpringFlow(final String value) {
        return Objects.equals(forValue(value), FlowType.SPRING);
    }

    /**
     * Determine whether input {@link FlowType} matches the {@code UNIT} test type
     *
     * @param value - initial input {@link String} value to check
     * @return {@code true} if it matches, {@code false} otherwise
     */
    public static boolean isUnitFlow(final String value) {
        return Objects.equals(forValue(value), FlowType.UNIT);
    }

    /**
     * Determine whether input {@link FlowType} matches the {@code FUNCTIONAL} test flow
     *
     * @param value - initial input {@link String} value to check
     * @return {@code true} if it matches, {@code false} otherwise
     */
    public static boolean isFunctionalFlow(final String value) {
        return Objects.equals(forValue(value), FlowType.FUNCTIONAL);
    }

    /**
     * Determine whether input {@link FlowType} matches the {@code INTEGRATION} test flow
     *
     * @param value - initial input {@link String} value to check
     * @return {@code true} if it matches, {@code false} otherwise
     */
    public static boolean isIntegrationFlow(final String value) {
        return Objects.equals(forValue(value), FlowType.INTEGRATION);
    }

    /**
     * Determine whether input {@link FlowType} matches the {@code OTHER} test flow
     *
     * @param value - initial input {@link String} value to check
     * @return {@code true} if it matches, {@code false} otherwise
     */
    public static boolean isOtherFlow(final String value) {
        return Objects.equals(forValue(value), FlowType.OTHER);
    }

    /**
     * Initialization of available test flows
     */
    static {
        params.put(FlowUnit.ACCEPTANCE_TEST, FlowType.ACCEPTANCE);
        params.put(FlowUnit.UNIT_TEST, FlowType.UNIT);
        params.put(FlowUnit.FUNCTIONAL_TEST, FlowType.FUNCTIONAL);
        params.put(FlowUnit.INTEGRATION_TEST, FlowType.INTEGRATION);
        params.put(FlowUnit.MOCK_TEST, FlowType.MOCK);
        params.put(FlowUnit.SPRING_TEST, FlowType.SPRING);
        params.put(FlowUnit.FLAKY_TEST, FlowType.FLAKY);
        params.put(FlowUnit.OTHER_TEST, FlowType.OTHER);
    }
}
