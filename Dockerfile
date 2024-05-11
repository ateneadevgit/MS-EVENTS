FROM openjdk:17-oracle
RUN mkdir data-shared
COPY target/ms-event.jar ms-event.jar
EXPOSE 8022
ENTRYPOINT ["java","-jar","/ms-event.jar"]