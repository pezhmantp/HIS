#!/bin/bash
# check-config-server-started.sh

apt-get update -y
yes | apt-get install curl

curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://localhost:8888/actuator/health)

echo "result status code:" "$curlResult"

while [[ ! $curlResult == "200" ]]; do
  >&2 echo "Config server is not up yet!"
  sleep 2
  curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://localhost:8888/actuator/health)
#  curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://cloud-config-server-docker-compose:8888/actuator/health)
done
/cnb/process/web
