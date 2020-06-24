# Base OS layer
FROM openjdk:8-jdk-alpine

LABEL org.opencontainers.image.title="SensibleMetrics.WS.Document.Generator"
LABEL org.opencontainers.image.vendor="SensibleMetrics, Inc."

# Install curl package
RUN apk --no-cache add curl

# The application's jar file
ARG JAR_FILE

# Add the application's jar to the container
ADD ${JAR_FILE} /opt/app/com.sensiblemetrics.api.ws.document.generator.jar

# Set workdir inside container
WORKDIR /opt/app

# Expose service default port
EXPOSE 5000

# Java environment options
ENV JAVA_OPTS="-Xms64m -Xmx1024m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true -Dcom.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize=true"

# Run microservice process.
ENTRYPOINT [ "java", "-jar", "com.sensiblemetrics.api.ws.document.generator.jar" ]
