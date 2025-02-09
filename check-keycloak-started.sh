#!/bin/bash
# check-keycloak-started.sh

apt-get update -y
yes | apt-get install curl

curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://localhost:8079/realms/master/.well-known/openid-configuration)

echo ">>>>>>>>>>>>result status code:" "$curlResult"

while [[ ! $curlResult == "200" ]]; do
  >&2 echo "Keycloak server is not ready"
  sleep 2
  curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://localhost:8079/realms/master/.well-known/openid-configuration)
done

/cnb/process/web
