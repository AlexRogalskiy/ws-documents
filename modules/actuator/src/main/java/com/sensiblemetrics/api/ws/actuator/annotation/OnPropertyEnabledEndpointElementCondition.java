package com.sensiblemetrics.api.ws.actuator.annotation;

import com.sensiblemetrics.api.ws.actuator.property.WsApiStatusProperty;
import org.springframework.boot.actuate.autoconfigure.OnEndpointElementCondition;

class OnPropertyEnabledEndpointElementCondition extends OnEndpointElementCondition {

    OnPropertyEnabledEndpointElementCondition() {
        super(WsApiStatusProperty.PROPERTY_PREFIX, ConditionalOnEndpointEnabled.class);
    }
}
