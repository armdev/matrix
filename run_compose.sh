#!/usr/bin/env bash

set -e

mvn clean package -U -Dmaven.test.skip=true
echo "Down old Docker containers"
docker-compose down
echo "Start all containers"
docker-compose  up -d --build
echo "New Release is Ready"
docker logs --follow gateway






