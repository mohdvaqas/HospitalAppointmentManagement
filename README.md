# Appointment Management System
![](https://user-images.githubusercontent.com/31319842/101862601-e6ea8c00-3b9c-11eb-8843-4bf612f97931.png)

## Overview
The architecture is composed by five services:

* [`api-getway`](https://github.com/mohdvaqas/HospitalAppointmentManagement/tree/main/api-gateway): Dockerizing **API Gateway Service** using docker and docker-compose.

* [`eureka-server`](https://github.com/mohdvaqas/HospitalAppointmentManagement/tree/main/eureka-server): Dockerizing **Eureka Service** using docker and docker-compose.

* [`appointment-service`](https://github.com/mohdvaqas/HospitalAppointmentManagement/tree/main/appointment-service): Dockerizing **Sales  Service** using docker and docker-compose application connection with two deferent database  **local** **database** and **docker database**

##### [`docker-compose`](https://github.com/ahsumon85/dockerized-spring-boot-microservice#deployment-on-docker-compose): Dockerizing microservice using docker-compose application connect with local database

### Tools you will need
* Maven 3.0+ is your build tool
* JDK 17
* Local Postgres SQL
* Docker version 19
* docker-compose version 1.27.4

### Create Dockerfile for all services

**Create  .jar with Maven by build application**

```
$ cd HospitalAppointmentManagement

$ pwd
/home/username/HospitalAppointmentManagement/

$ ls
eureka-service  api-gateway  appointment-service  pom.xml

$ mvn clean install
```

**Run the Spring Boot application using terminal**

```
$ java -jar eureka-service/target/eureka.jar 
$ java -jar api-gateway/target/gateway.jar 
$ java -jar appointment-service/target/appointment.jar
```

**Next, we containerize this application by adding a `Dockerfile`:**

See the `Dockerfile` at the root of the project? We only need this `Dockerfile` text file to dockerize the Spring Boot application. now we will cover the two most commonly used approaches:

- **Dockerfile** – Specifying a file that contains native Docker commands to build the image
- **Maven** – Using a Maven plugin to build the image

Below we create simple `Dockerfile` under the project

### Docker File

#### FORM

A `Dockerfile` is a text file, contains all the commands to assemble the docker image. Review the commands in the `Dockerfile`:

It creates a docker image base on `openjdk:17` and download `jdk` from Docker Hub This base image `openjdk:17` is just an example, we can find more base images from the official [Docker Hub](https://hub.docker.com/r/adoptopenjdk/openjdk11)

```
FROM openjdk:17
```

#### VOLUME

A volume is a persistent data stored it used to stored log file into the local directory in the define location

```
VOLUME /app/log
```

#### ADD

This tells Docker to copy files from the local file-system to a specific folder inside the build image. Here, we copy our `.jar` file to the build image inside `target/X.X.0.1.jar`

The `ADD` command requires a `source` and a `destination`.

If `source` is a file, it is simply copied to the `destination` directory.

```
ADD target/X.X.jar X.X.jar
```

- If `source` is a file, it is simply copied to the `destination` directory.
- If `source` is a directory, its *contents* are copied to the `destination`, but the directory itself is not copied.
- `source` can be either a tarball or a URL (as well).
- `source` needs to be within the directory where the `docker build` command was run.
- Multiple sources can be used in one `ADD` command.

#### EXPOSE

Writing `EXPOSE` in your Dockerfile, is merely a hint that a certain port is useful. Docker won’t do anything with that information by itself.

Defining a port as “exposed” doesn’t publish the port by itself.

```
EXPOSE 8380
```

#### ENTRYPOINT

Run the jar file with `ENTRYPOINT`.

`<instruction> [“executable”, “parameter”]`

`ENTRYPOINT ["echo", "Hello World"] (exec form)`

```
ENTRYPOINT ["java", "-jar", "X.X.jar"]
```



## Eureka Service

Eureka Server is an application that holds the information about all client-service applications. Every Micro service will register into the Eureka server and Eureka server knows all the client applications running on each port and IP address. Eureka Server is also known as Discovery Server.

### Dockerizing the Eureka Service using `Dockerfile`


### Build the image using this Dockerfile.

**Move to the root directory of the application and run this command:**

```
$ cd eureka-service/

$ pwd
/home/username/HospitalAppointmentManagement/eureka-service

$ ls
Dockerfile  pom.xml  src  target

$ docker build . -t eureka:0.1
```

### Run docker eureka-server image

Start the docker container `eureka-server:0.1`, run the `micro-eureka-service/target/micro-eureka-service-0.0.1-SNAPSHOT.jar` file during startup.

- Add `run -d` to start the container in detach mode – run the container in the background.
- Add `run -p` to map ports.
- Add `run --name` to create container name
- Add `eureka-server:0.1 ` image name

**After successfully run then we will refresh  Eureka Discovery-Service URL: `http://localhost:8761`**

## API Gateway Service

Gateway Server is an application that transmit all API to desire services. every resource services information such us: service-name, context-path will beconfigured into the gateway service and every request will transmit configured services by gateway

## Appointment Service

Appointment service would be responsible for the following REST endpoint

Controller | METHOD | Endpoint                        | Request body                                                                                                                                                           | Response                                                                                                                                                                                              | Sample Curl request
--- |--------|---------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------
PatientController | POST   | patient/create                  | {"name": "Mohammad Vaqas","email": "mohd.vaqas@gmail.com","age": 18,"gender": 1}                                                                                       | {"id": 1,"name": "Mohammad Vaqas","email": "mohd.vaqas@gmail.com","age": 18,"gender": 1,"createdDate": "2023-06-08T12:01:11.523+00:00"}                                                               | curl --location 'http://localhost:9999/proxy-patient/create' \--header 'Content-Type: application/json' \--data-raw '{"name": "Mohammad Vaqas","email": "mohd.vaqas@gmail.com","age": 18,"gender": 1}'
PatientController | GET    | patient/appointment/{patientId} | {"patientId": 1,"appointmentDateTime": "2023-06-09 12:12:12"}                                                                                                          | {"id": 1,"status": "ACTIVE","appointmentDateTime": "2023-06-09T12:12:12.000+00:00","createdDate": "2023-06-08T12:01:19.671+00:00","lastModifiedDate": "2023-06-08T12:01:19.671+00:00","reason": null} | curl --location 'http://localhost:9999/proxy-patient/appointment/{patientId}' \--header 'Content-Type: application/json' \--data '{"patientId": 1,"appointmentDateTime": "2023-06-09 12:12:12"}'
PatientController | POST   | appointment/create              |                                                                                                                                                                        | {"identityNumber": 1,"name": "Mohammad Vaqas","email": "mohd.vaqas@gmail.com","appointmentResponse": [],"createdDate": "2023-06-08T12:01:11.523+00:00"}                                               | curl --location 'http://localhost:9999/proxy-appointment/create' \--header 'Content-Type: application/json' \--data '{"patientId": 1,"appointmentDateTime": "2023-06-09 12:12:12"}'
AppointmentController | PATCH  | appointment/update              | {"appointmentId": 1,"patientId": 1,"status": 2,"reason": "NNNVaqas"}                                                                                                   | {"id": 1,"status": "PATIENT_DID_NOT_ARRIVE","appointmentDateTime": null,"createdDate": "2023-06-08T12:01:19.671+00:00","lastModifiedDate": null,"reason": "NNNVaqas"}                                 | curl --location --request PATCH 'http://localhost:9999/proxy-appointment/update' \--header 'Content-Type: application/json' \--data '{"appointmentId": 1,"patientId": 1,"status": 2,"reason": "NNNVaqas"}'
AppointmentController | GET    | appointment/search              | {"patientId": 17,"appointmentDateTime": "2023-06-09 12:12:12","status": 2,"dateFrom":"2023-06-08","dateTo":"2023-06-09","name":"Vaqas","pageNumber": 0,"pageSize": 20} | {"id": 1,"status": "ACTIVE","appointmentDateTime": "2023-06-09T12:12:12.000+00:00","createdDate": "2023-06-08T12:01:19.671+00:00","lastModifiedDate": "2023-06-08T12:01:19.671+00:00","reason": null} | curl --location 'http://localhost:9999/proxy-appointment/search' \--header 'Content-Type: application/json' \--data '{"patientId": 1,"appointmentDateTime": "2023-06-09 12:12:12"}'
AppointmentController | DELETE | appointment/delete?appointmentId=2 | BLANK | BLANK                                                                                                                                                                                                 | curl --location --request DELETE 'http://localhost:9999/proxy-appointment/delete?appointmentId=2' \--header 'Content-Type: application/json' \--data '{"patientId": 17,"appointmentDateTime": "2023-06-09 12:12:12"}'

Search Service is multi funcaionality service it is validing the object in the VM and by using spring specification interface
created the paginated dynamic query process.

I wanted to implement the OAuth integration for Admin login on the home, the home page will display the last records of today 