@echo off

docker pull prom/pushgateway
docker run -d -p 9091:9091 prom/pushgateway
