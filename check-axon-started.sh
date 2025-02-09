#!/bin/bash
# check-axon-started.sh

apt-get update -y
yes | apt-get install curl

curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://localhost:8024/actuator/health)

echo ">>>>>>>>>>>>result status code:" "$curlResult"

while [[ ! $curlResult == "200" ]]; do
  >&2 echo "axon server is not ready"
  sleep 2
  curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://localhost:8024/actuator/health)
done

/cnb/process/web
