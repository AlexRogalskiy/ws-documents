# README

![](https://github.com/AlexRogalskiy/ws-documents/workflows/Document-Generator-Web-Service/badge.svg??branch=master&event=push) ![Create Release](https://github.com/AlexRogalskiy/ws-documents/workflows/Document-Generator-Web-Service-release/badge.svg?branch=master&event=push)

## Summary

Web service intended to provide operations on documents \(create, edit, fetch document's data and generate file in docx format by template\)

## Description

Application can be used for creating and editing document's data with possibility to generate final DocX-document by template

## Compile

### For JDK 8

```text
mvnw clean install spring-boot:repackage -Pnon_module_java,test-jar,xsd -DskipTests
```

to build image to Docker daemon:

```text
mvnw clean install -Pnon_module_java,test-jar,xsd,jib -DskipTests
```

### For JDK 11

```text
mvnw clean install spring-boot:repackage -Pmodule_java,test-jar,xsd,jib -DskipTests
```

building image to Docker daemon:

```text
mvnw clean package -Pmodule_java,test-jar,xsd,jib -DskipTests
```

buidling & deploying docker image to DockerHub:

```text
mvnw -s settings.xml clean package -Pmodule_java,test-jar,xsd,jib -DskipTests -Denv.DOCKERHUB_USERNAME=<username> -Denv.DOCKERHUB_PASSWORD=<password>
```

run local build/deployment process:

```text
skaffold config set --global local-cluster true
skaffold dev --trigger notify
```

## Run with Skaffold

In order to run the `ws-documents` service using _skaffold_, you need to have the _DocumentDB_ up and running:

#### 1. Start the document DB \(postgres\):

```text
skaffold run -p documents-db-local
```

#### 2. Start the ws-documents service:

```text
skaffold run -p local
```

## Run with containerized PostgreSQL

Run PostgreSQL in docker-container via command:

```text
docker run --name db-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=documentdb -p 5432:5432 -d postgres
```

```text
cd ${project.build.directory}/repackage
java -jar com.sensiblemetrics.api.ws-document-generator-0.1.0-SNAPSHOT-exec.jar
```

where

```text
project.build.directory=modules/document-generator/.build/bin/com.sensiblemetrics.api.ws.document.generator
```

or simply run:

```text
scripts/run.bat
```

## Deploy with Kubectl

#### 1. Start the document service:

```text
kubectl run spring-boot-jib --image=$IMAGE --port=8080 --restart=Never
```

#### 2. Wait until pod is up and running:

```text
kubectl port-forward spring-boot-jib 8080
```

## Download Docker image \(RegistryHub\)

```text
docker pull alexanderr/ws-documents:0.1.0-SNAPSHOT
```

## Authors

WS-Documents is maintained by:
* [Alexander Rogalskiy](https://github.com/AlexRogalskiy) 

with community support please contact with us if you have some question or proposition.

### Version Store

[./pom.xml](https://github.com/AlexRogalskiy/ws-documents/blob/master/pom.xml)

## Team Tools

[![alt tag](http://pylonsproject.org/img/logo-jetbrains.png)](https://www.jetbrains.com/) 

SensibleMetrics Team would like inform that JetBrains is helping by provided IDE to develop the application. Thanks to its support program for an Open Source projects!

[![alt tag](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/dashboard?id=org.schemaspy%3Aschemaspy)

SensibleMetrics WS-Documents project is using SonarCloud for code quality. 
Thanks to SonarQube Team for free analysis solution for open source projects.

## License
SensibleMetrics WS-Documents is distributed under LGPL version 3 or later, see COPYING.LESSER(LGPL) and COPYING(GPL).   
LGPLv3 is additional permissions on top of GPLv3.

![image](https://user-images.githubusercontent.com/19885116/48661948-6cf97e80-ea7a-11e8-97e7-b45332a13e49.png)
