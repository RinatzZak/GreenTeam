FROM openjdk:11
COPY target/CurrencyService-1.0-SNAPSHOT-shaded.jar currency.jar
EXPOSE 8000:8082
ENTRYPOINT ["java", "-jar", "/currency.jar"]