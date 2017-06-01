# rest-scala-akka-reactive-programming
Proof of concept of API build on scala and on a actor akka framework

## Description
This is a proof of concept on scala of a simple rest application, based on a fraud software to detect
big amount of quantities of the transactions, that are received on the application by rest calls.

The incoming requests are handled by an actor that this invoke a pool of actors to process this information.
At the same time, those actors, when detect a fraud candidate throws this message as sysout, ideally need to
sent the message to another downstream system.

## Software used

Scala version 2.11.8

Akka version 2.4.1

## Project structure

The main class of the project is:
```
com.reactive.service.fraud.rest.FraudServiceRest
```

The packages are stuctured as:
- *com.reactive.service.fraud*: Main package of the application with all the logic of the endpoints + akka
- *com.reactive.service.examples*: examples used in part on the project, that are simple examples that may be of used for other users
- *com.reactive.service.model*: data model used on the application

## Testing
As this is not recent project, this has not been created in a TDD fashion and the test are done on a jmeter.

The jmx file can be found on the jmx folder on the same project. This is the integration test used to 
validate that the project is working.