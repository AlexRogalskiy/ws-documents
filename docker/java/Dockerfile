# Base OS layer
# docker build -t container_tag --build-arg IMAGE_SOURCE=openjdk IMAGE_TAG=latest .
ARG IMAGE_SOURCE=openjdk
ARG IMAGE_TAG=latest
FROM ${IMAGE_SOURCE}:${IMAGE_TAG}

RUN groupadd spring && useradd -m -g spring -s /bin/bash spring
USER spring:spring

VOLUME /tmp

LABEL org.opencontainers.image.title="SensibleMetrics.WebDocs"
LABEL org.opencontainers.image.description="SpringBoot WebDocs WebService application"
LABEL org.opencontainers.image.vendor="SensibleMetrics, Inc."
LABEL org.opencontainers.image.version="1.0"

# The application's jar file
ARG JAR_FILE

# Add the application's jar to the container
ADD ${JAR_FILE} /opt/app/com.sensiblemetrics.api.ws.document.generator.jar

# Set workdir inside container
WORKDIR /opt/app

# Expose service default port
EXPOSE 5000

# General environment options
ENV TERM xterm

# Java environment options
ENV JASYPT_ENCRYPTOR_PASSWORD=the_password
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
ENV JAVA_OPTS="-Dspring.profiles.active=default -Djava.security.egd=file:/dev/./urandom -Djava.net.preferIPv4Stack=true -Dcom.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize=true"
ENV JAVA_VM_OPTS="-Xms64m -Xmx1024m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -XX:NativeMemoryTracking=summary -XX:+UseConcMarkSweepGC"

# Run microservice process.
ENTRYPOINT [ "java", "-jar", "com.sensiblemetrics.api.ws.document.generator.jar" ]
