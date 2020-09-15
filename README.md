![](https://github.com/AlexRogalskiy/ws-documents/workflows/Document-Generator-Web-Service/badge.svg??branch=master&event=push)
![Create Release](https://github.com/AlexRogalskiy/ws-documents/workflows/Document-Generator-Web-Service-release/badge.svg?branch=master&event=push)

# Summary

Web service intended to provide operations on documents (create, edit, fetch document's data and generate file in docx format by template)

# Description

Application can be used for creating and editing document's data with possibility to generate final DocX-document by template

# Compile

## For JDK 8

```
mvn clean install spring-boot:repackage -Pnon_module_java,test-jar,xsd -DskipTests
```

to build image to Docker daemon:

```
mvn clean install -Pnon_module_java,test-jar,xsd,jib -DskipTests
```

## For JDK 11

```
mvn clean install spring-boot:repackage -Pmodule_java,test-jar,xsd,jib -DskipTests
```

building image to Docker daemon:

```
mvn clean package -Pmodule_java,test-jar,xsd,jib -DskipTests
```

buidling & deploying docker image to DockerHub:

```
mvn -s settings.xml clean package -Pmodule_java,test-jar,xsd,jib -DskipTests -Denv.DOCKERHUB_USERNAME=<username> -Denv.DOCKERHUB_PASSWORD=<password>
```

run local build/deployment process:

```
skaffold config set --global local-cluster true
skaffold dev --trigger notify
```

# Run with Skaffold

In order to run the `ws-documents` service using *skaffold*, you need to have the *DocumentDB* up and running:

#####1. Start the document DB (postgres):
```
skaffold run -p documents-db-local
```

#####2. Start the ws-documents service:
```
skaffold run -p local
```

# Execute

Run PostgreSQL in docker-container via command:

```
docker run --name db-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=documentdb -p 5432:5432 -d postgres
```

```
cd ${project.build.directory}/repackage
java -jar com.sensiblemetrics.api.ws-document-generator-0.1.0-SNAPSHOT-exec.jar
```

where

```
project.build.directory=modules/document-generator/.build/bin/com.sensiblemetrics.api.ws.document.generator
```

or simply run:

```
run.bat
```

# Deploy with Kubectl:

#####1. Start the document service:
```
kubectl run spring-boot-jib --image=$IMAGE --port=8080 --restart=Never
```

#####2. Wait until pod is up and running:
```
kubectl port-forward spring-boot-jib 8080
```

# Authors

Alexander Rogalskiy

# License

proprietary

## Version Store

[./pom.xml](./pom.xml)
