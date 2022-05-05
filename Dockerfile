FROM arm64v8/amazoncorretto:17

WORKDIR /app

COPY build/libs/app.jar .

CMD java -jar app.jar
