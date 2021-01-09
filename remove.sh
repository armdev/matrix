#!/bin/bash


if [ $# -lt 1 ]
then
        echo "Usage : ./remove.sh microservicename"
        exit
fi

docker rm -f $1
