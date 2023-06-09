FROM maven:3.8.3-openjdk-17 as maven
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package

FROM openjdk:17
EXPOSE 1009
ADD target/appointment-service.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]