FROM amazoncorretto:17-alpine

WORKDIR /app

COPY build/libs/app.jar .

CMD java -jar app.jar
