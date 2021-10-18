#!/bin/bash


if [ $# -lt 1 ]
then
        echo "Usage : ./restart.sh microservicename"
        exit
fi

mvn clean install -pl $1  -am -DskipTests=true

sudo docker rm -f $1
#sudo docker rmi -f $1
sudo docker-compose up -d --no-deps --build $1

sudo docker logs --follow $1


