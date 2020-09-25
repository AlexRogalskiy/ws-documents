# Base OS layer
FROM openjdk:latest

RUN groupadd spring && useradd -m -G spring -s /bin/bash spring
USER spring:spring

VOLUME /tmp

LABEL org.opencontainers.image.title="SensibleMetrics.WebService.Document.Generator"
LABEL org.opencontainers.image.vendor="SensibleMetrics, Inc."

# The application's jar file
ARG JAR_FILE

# Add the application's jar to the container
ADD ${JAR_FILE} /opt/app/com.sensiblemetrics.api.ws.document.generator.jar

# Set workdir inside container
WORKDIR /opt/app

# Expose service default port
EXPOSE 5000

# Java environment options
ENV JAVA_OPTS="-Xms64m -Xmx1024m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.security.egd=file:/dev/./urandom -Djava.net.preferIPv4Stack=true -Dcom.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize=true"
ENV TERM xterm

# Run microservice process.
ENTRYPOINT [ "java", "-jar", "com.sensiblemetrics.api.ws.document.generator.jar" ]
