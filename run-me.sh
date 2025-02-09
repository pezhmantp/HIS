#!/bin/bash

find * -name pom.xml -execdir mvn clean install -Dmaven.test.skip=True \;

docker-compose -f docker-compose.yml up -d