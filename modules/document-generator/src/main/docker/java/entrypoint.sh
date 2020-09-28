#!/usr/bin/env bash

while ! nc -z config-server 8888 ; do
    echo "Waiting for upcoming Config Server"
    sleep 2
done

# Java environment options
ENV JAVA_VM_OPTS="-Xms64m -Xmx1024m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -XX:NativeMemoryTracking=summary -XX:+UseConcMarkSweepGC"
ENV JAVA_OPTS="-Dspring.profiles.active=default -Djava.security.egd=file:/dev/./urandom -Djava.net.preferIPv4Stack=true -Dcom.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize=true"

java ${JAVA_VM_OPTS} ${JAVA_OPTS} -jar /com.sensiblemetrics.api.ws.document.generator.jar
