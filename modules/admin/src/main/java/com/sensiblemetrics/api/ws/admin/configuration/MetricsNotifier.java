package com.sensiblemetrics.api.ws.admin.configuration;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

@Slf4j
public class MetricsNotifier extends AbstractEventNotifier {

    public MetricsNotifier(final InstanceRepository repository) {
        super(repository);
    }

    @NonNull
    @Override
    protected Mono<Void> doNotify(@NonNull InstanceEvent event,
                                  @NonNull Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                log.info("Instance {} ({}) is {}", instance.getRegistration().getName(), event.getInstance(), ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
            } else {
                log.info("Instance {} ({}) {}", instance.getRegistration().getName(), event.getInstance(), event.getType());
            }
        });
    }
}
