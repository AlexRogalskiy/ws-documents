package com.sensiblemetrics.api.ws.document.uploader.model.domain;

import com.sensiblemetrics.api.ws.document.uploader.enumeration.StatusType;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Data
@Value(staticConstructor = "of")
public class MessageData implements Serializable {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 6479100880398576258L;

    /**
     * Document identifier
     */
    private UUID documentId;

    /**
     * Document status type
     */
    private StatusType status;
}
