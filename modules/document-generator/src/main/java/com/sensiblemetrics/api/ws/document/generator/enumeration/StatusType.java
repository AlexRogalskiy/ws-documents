package com.sensiblemetrics.api.ws.document.generator.enumeration;

import com.google.common.collect.Sets;

import java.util.Set;

public enum StatusType {
    CANCELLED,
    COMPLETED,
    NEW,
    PROCESSING,
    REGISTERED,
    SUSPENDED,
    EDITED;

    /**
     * Available {@link StatusType}s
     */
    public static final Set<StatusType> ACTIVE_STATUS_SET = Sets.newHashSet(NEW, SUSPENDED, EDITED);

    /**
     * Returns binary value whether current status is active (@code NEW|SUSPENDED|EDITED)
     *
     * @param statusType initial input {@link StatusType} to operate by
     * @return true - if current status is active, false - otherwise
     */
    public static boolean isAvailable(final StatusType statusType) {
        return ACTIVE_STATUS_SET.contains(statusType);
    }
}
