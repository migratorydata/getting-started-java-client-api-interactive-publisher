#### Getting started with MigratoryData Java Client API V6 Interactive Publisher

##### Prerequisites
Java Development Kit (JDK) 8+, Gradle 6+ 

#### Clone Project

Clone the getting started project from GitHub using your IDE of choice or using the following command:
> git clone https://github.com/migratorydata/getting-started-java-client-api-interactive-publisher.git

#### Configure
Update the code snippet from the file src/main/java/com/migratorydata/example/Config.java to your needs.

#### Build & Run
Use the following commands to build and run your project:
> ./gradlew clean build

> ./gradlew run

#### Test

Connect to the MigratoryData Debug Console using url `http://{ip}:8800/console.html` and subscribe to a subject that starts with `wildcardSubject` set in the `Config.java` file.

The client should receive messages published from this publisher.