## Micros appointment (soon renamed to candy-shop-micros)
___
- The idea is to build simple microservice app which will be friendly to develop for front and backend developers.
- For now there will be 2 microservices. 1 responsible for bearer token generation and user registration, 2nd responsible for candy resource.
- App is build with java 17 and quarkus framework.
___
If you want to run the app:
1. Make sure your system is capable of running make scripts (Makefile). All linux, mac oses will do. Makefile should also run on Windows but it might require installing some 'extra' software.
2. Make sure your system has docker and docker-compose installed.

3. To run whole microservice app:
   - Run following commands in the main catalogue of the whole project:
      - `make dev-env-clean`
      - `make dev-env-up`
4. To develop certain microservice:
   - make sure you have got JDK 17 and maven installed,
   - run following commands in the catalogue of certain service: 
   
     - `make dev-env-clean`
     - `make dev-env-up`
     - `mvn clean compile` or `./mvnw clean compile` or `.\mvnw.cmd clean compile`
     - `mvn quarkus:dev` or `./mvnvw quarkus:dev` or `.\mvnw.cmd quarkus:dev`
   - Now the certain serivce which you want to develop should be up and running in develop mode which has Code Live Reload enabled. If u change the code just send another request at any endpoint. App will be recompile if any source changed.
___
If makefile is not working for you for some reason you can replace sequence of: `make dev-env-clean`, `make dev-env-up`
by copy-pasting commands from makefile. Pay attention from which makefile you copy because they might slightly differ. Please make sure you have docker, docker-compose installed at least.
___
1. When you are developing certain service it is mostly likely to run on localhost:8080.
2. If you run the whole app with `make dev-env-up` fromm main directory of the project services will be bind to your localhost on following ports:
   1. security-micro: localhost:8080
   2. Next services will be listed here
___
      Later on to run the whole app it might require to have minikube installed so the kubernetes ingress component will take care of the routing.




