# Appointment Management System
![](https://user-images.githubusercontent.com/31319842/101862601-e6ea8c00-3b9c-11eb-8843-4bf612f97931.png)

## Overview
The architecture is composed by three services:

* [`api-getway`](https://github.com/mohdvaqas/HospitalAppointmentManagement/tree/main/api-gateway): Dockerizing **API Gateway Service** using docker and docker-compose.

* [`eureka-server`](https://github.com/mohdvaqas/HospitalAppointmentManagement/tree/main/eureka-server): Dockerizing **Eureka Service** using docker and docker-compose.

* [`appointment-service`](https://github.com/mohdvaqas/HospitalAppointmentManagement/tree/main/appointment-service): Dockerizing **Appointment Service** using docker and docker-compose application connection with two deferent database  **local** **database** and **docker database**

##### [`docker-compose`](https://github.com/mohdvaqas/HospitalAppointmentManagement/blob/main/docker-compose.yaml): Dockerizing microservice using docker-compose application connect with local database

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
$ java -jar api-gateway/target/api-gateway.jar 
$ java -jar appointment-service/target/appointment-service.jar
```

## Eureka Service

Eureka Server is an application that holds the information about all client-service applications. Every Micro service will register into the Eureka server and Eureka server knows all the client applications running on each port and IP address. Eureka Server is also known as Discovery Server.

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


## Steps to run the application
1. cd HospitalAppointmentManagement
2. Execute mvn clean install or docker build .
3. 
```
$ java -jar eureka-service/target/eureka.jar
$ java -jar api-gateway/target/api-gateway.jar
$ java -jar appointment-service/target/appointment-service.jar
```
3. docker-compose up -d (this will start the database container)
4. Import the postman collection on postman
5. The open API documentation will be running at http://localhost:1069/swagger-ui/index.html#