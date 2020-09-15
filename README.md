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
mvn clean install jib:dockerBuild -Pnon_module_java,test-jar,xsd -DskipTests
```

## For JDK 11

```
mvn clean install spring-boot:repackage -Pmodule_java,test-jar,xsd -DskipTests
```

building image to Docker daemon:

```
mvn clean install jib:dockerBuild -Pmodule_java,test-jar,xsd -DskipTests
```

buidling & deploying docker image to DockerHub:

```
mvn -s settings.xml clean install jib:build -Pmodule_java,test-jar,xsd -DskipTests -Denv.DOCKERHUB_USERNAME=<username> -Denv.DOCKERHUB_PASSWORD=<password>
```

run local build/deployment process:

```
skaffold config set --global local-cluster true
```

# Run with Skaffold

In order to run the `ws-documents` service using *skaffold*, you need to have the *DocumentDB* up and running:

#####1 . Start the document DB (postgres)
```
skaffold run -p documents-db-local
```

#####2. Start the ws-documents service
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

# Authors

Alexander Rogalskiy

# License

proprietary

## Version Store

[./pom.xml](./pom.xml)
