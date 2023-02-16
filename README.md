# NEXPAY task

## Usage

### Build
```jvm
mvn install 
```
This command compiles and builds the micro service packaged as WAR executable file.

### Run
```jvm
java -jar task-0.0.1-SNAPSHOT.war
```
This command executes the WAR package 

### Access
 ```jvm
http://localhost:8080/
 ```

After successful deployment the application runs locally on port 8080

### Tests Report
 ```jvm
mvn surefire-report:report  
 ```
This command executes all tests and generates HTML report at ==target/site/surefire-report.html==

## Task details
* **Write a microservice to determine the country by phone number.**

*Micro service implementation technical details:*
> - Java 17
> - SpringBoot 2.7.8
> - Maven
> - MVC with JSP
> - REST API with application/json
> - HTML, JavaScript, JQuery
> - CSS with Bootstrap

* **The application must be built and run from the command line, on port 8080.**

*Micro service deployment technical details:*
> - Application runs as executable WAR file on port 8080

* **It should also be possible to run tests and view reports on them.**

*Application business logic is covered by tests with test execution report:*
> - MVC and REST endpoints are covered by integration tests
> - Service functionality is covered by unit tests
> - Surefire plugin generates test execution report

* **All calls to the application are done using REST-WS with JSON as the data format.**

*REST API header details:*
> - content-type: application/json
> - accept: application/json

* **The appearance of the interface is unimportant, plain HTML will be fine.
For requests, use any AJAX-capable framework, you can just JQuery.**

*Frontend technical details:*
> - HTML 
> - Bootstrap CSS framework
> - Javascript
> - JQuery plugin for AJAX requests to the backend

* **Data validation, tests are required.The user enters a phone number, the system validates it and displays the country or error message.**

*REST API request payload validation details:*
> - JAVAX validation
> - 400 HTTP response code for invalid payload
> - User friendly error messages