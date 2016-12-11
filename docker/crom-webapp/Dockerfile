FROM openjdk:8
COPY webapp.jar /var/webapp.jar
WORKDIR /var
EXPOSE 8080
CMD ["java", "-Xmx1280m", "-Xms768m", "-server", "-jar", "/var/webapp.jar"]
