@Echo Off

rem setting environment variables
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres
set DB_PASS=password

rem running postgresql docker image
docker run --name db-postgres -e POSTGRES_USER=%DB_USER% -e POSTGRES_PASSWORD=%DB_PASS% -e POSTGRES_DB=documentdb -p %DB_PORT%:5432 -d postgres

rem java web service application
start javaw -Xms1024m -Xmx1024m -jar ./modules/document-generator/.build/bin/com.sensiblemetrics.api.ws.document.generator/repackage/com.sensiblemetrics.api.ws-document-generator-0.1.0-SNAPSHOT-exec.jar
