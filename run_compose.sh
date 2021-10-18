#!/usr/bin/env bash

set -e

mvn clean package -U -Dmaven.test.skip=true
echo "Down old Docker containers"
sudo docker-compose down
echo "Start all containers"
sudo  docker-compose  up -d --build
echo "New Release is Ready"
sudo docker logs --follow gateway






