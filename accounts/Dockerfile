#Start with base imagecontaining java runtime
FROM openjdk:17-jdk-slim

#Information around who maintains the image
MAINTAINER raj

#Add the application jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]
