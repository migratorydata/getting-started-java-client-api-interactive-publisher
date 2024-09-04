#### Getting started with MigratoryData Java Client API V6 Interactive Publisher

##### Prerequisites
Java Development Kit (JDK) 8+, Gradle 6+ 

#### Clone Project

Clone the getting started project from GitHub using your IDE of choice or using the following command:
> git clone https://github.com/migratorydata/getting-started-java-client-api-interactive-publisher.git

#### Start MigratoryData Server using docker

If you don't have a MigratoryData server installed on your machine but there is docker installed you can run the following command to start MigratoryData server, otherwise you can download and install the latest version for your os from [here](https://migratorydata.com/downloads/migratorydata-6/).

Run the following commands to start MigratoryData server with interactive publishing enabled:

```sh
docker pull migratorydata/server:latest
docker run -d -e MIGRATORYDATA_EXTRA_OPTS='-DExtension.InteractivePublishing=true' --name my_migratorydata -p 8800:8800 migratorydata/server:latest
```

#### Configure
Update the code snippet from the file `src/main/java/com/migratorydata/example/Config.java` to your needs.

#### Build & Run
Use the following commands to build and run your project:

```sh
./gradlew clean build
./gradlew run
```

#### Test

Connect to the MigratoryData Debug Console using url `http://127.0.0.1:8800/console.html` and subscribe to a subject that starts with `wildcardSubject` set in the `Config.java` file.
If default subject is used, subscribe to `/server/status` subject and the client should receive messages published from this publisher.
