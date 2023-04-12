FROM eclipse-temurin:17-jdk-alpine

MAINTAINER yujuncai

RUN mkdir -p /home/work

WORKDIR /home/work

VOLUME /tmp

EXPOSE 8080 80 45588/udp

COPY ./server-1.0-SNAPSHOT.jar /home/work/tia.jar

ENTRYPOINT ["java","-jar","tia.jar"]