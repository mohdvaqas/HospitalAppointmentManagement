FROM maven:3.8.3-openjdk-17 as maven
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean install

FROM openjdk:17
EXPOSE 8761
RUN echo "$PWD"
ADD target/eureka.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]