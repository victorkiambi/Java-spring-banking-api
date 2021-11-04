##banking api 

###Initializing project with docker
1. Run docker network create banking-api
2. docker run --name db --network banking-api -e MYSQL_USER=springuser -e MYSQL_DATABASE=ThePassword -d mysql:8.0
3. docker  run --network banking-api --name spring-banking-api -p 8081:8081 -d spring-banking-api

###Regular initialization
1. Open application in your favorite editor
2. Make sure you have mysql installed
3. Run app via mvn spring-boot:run

- Import SaltPay-Banking-Api.postman_collection.json to postman.
- Make requests via postman
