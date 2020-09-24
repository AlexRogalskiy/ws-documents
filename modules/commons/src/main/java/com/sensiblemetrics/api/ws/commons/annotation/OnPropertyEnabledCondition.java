package com.sensiblemetrics.api.ws.commons.annotation;

import com.sensiblemetrics.api.ws.commons.property.WsApiStatusProperty;
import org.springframework.boot.actuate.autoconfigure.OnEndpointElementCondition;

class OnPropertyEnabledCondition extends OnEndpointElementCondition {

    OnPropertyEnabledCondition() {
        super(WsApiStatusProperty.PROPERTY_PREFIX, ConditionalOnPropertyEnabled.class);
    }
}
