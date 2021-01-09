#!/usr/bin/env bash

set -e
echo "Starting stack, access database via PC IP => http://192.168.1.7:7474/browser/"
export NEO4J_HOST_URI=192.168.1.7
mvn clean install -pl friend -am -DskipTests=true
docker-compose build
docker stack up matrix --compose-file stack.yaml --orchestrator swarm
