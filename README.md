team-expense-manager
====================

> This project is an example of using Hibernate with Spring Boot.

> Main ideas of the project is an application to manage all expenses and funds of a team.

![Team expense management schema diagram](doc/TeamExpenseManager.png "Team expense management schema diagram")

## Prerequisites

* Java 8
* Maven 3
* [Angular CLI](https://github.com/angular/angular-cli)

## Build UI package:
```
cd ui
ng build
```
The build artifacts will be stored in the `../src/main/resources/public/` directory.

## Run this project by execute the following command:
```
# Navigate back to project root folder
cd ..
# Run spring-boot
mvn spring-boot:run
```

Visit http://localhost:8080

Api documentation: http://localhost:8080/swagger-ui.html

