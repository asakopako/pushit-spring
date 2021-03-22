#!/usr/bin/env bash

docker stop pushit
docker rm pushit
docker rmi pushit
cd /root/pushit/project
bash -x gradlew buildDocker --no-daemon --stacktrace -Dprod -Pprofile=prod -x test
docker logs -f jazzit