#!/usr/bin/env bash

set -e

echo "Start only neo4j, for access from localhost"
docker-compose  up -d --build neo4j







