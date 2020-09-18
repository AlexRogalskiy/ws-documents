@echo off

rem initializing environment variables
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres
set DB_PASS=password
set DB_NAME=documentdb

rem initializing generator environment variables
set DOC_STORAGE_PATH=~/home
set DOC_TEMPLATE_NAME=template/template.docx

rem running postgresql docker image
docker run --name db-postgres -e POSTGRES_USER=%DB_USER% -e POSTGRES_PASSWORD=%DB_PASS% -e POSTGRES_DB=%DB_NAME% -p %DB_PORT%:5432 -d postgres

SetLocal
rem java path
IF ["%JAVA_HOME%"] EQU [""] (
	set JAVA=java
) ELSE (
	set JAVA="%JAVA_HOME%/bin/javaw"
)

rem java web service application options
set JAVA_OPTS=-Xms1024m -Xmx1024m

rem java web service application options
set JAVA_COMMAND=-jar ./modules/document-generator/.build/bin/com.sensiblemetrics.api.ws.document.generator/repackage/com.sensiblemetrics.api.ws-document-generator-0.1.0-SNAPSHOT-exec.jar

rem java web service startup
start %JAVA% %JAVA_OPTS% %JAVA_COMMAND% 1>log.txt 2>err.txt
EndLocal
