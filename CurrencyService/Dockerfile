FROM openjdk:11
COPY target/CurrencyService-1.0-SNAPSHOT-shaded.jar currency.jar
ENTRYPOINT ["java", "-jar", "/currency.jar"]