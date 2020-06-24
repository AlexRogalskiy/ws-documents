# Summary

Web service intended to provide operations on documents (create, edit, fetch document's data and generate file in docx format by template)

# Description

Application can be used for creating and editing document's data with possibility to generate final docx document by template

# Compile

```
mvn clean install spring-boot:repackage -Ptest-jar,xsd -DskipTests
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
