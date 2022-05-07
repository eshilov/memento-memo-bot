FROM amazoncorretto:17-alpine

WORKDIR /app

COPY build/libs/app.jar .

COPY .docker/docker-cmd.sh .

RUN chmod +x docker-cmd.sh

COPY .docker/setup setup

RUN chmod -R +x setup

RUN apk add --no-cache bash

CMD "./docker-cmd.sh"
