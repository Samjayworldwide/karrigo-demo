FROM eclipse-temurin:17

ADD target/karriGo-BE-0.0.1-SNAPSHOT.jar karrigo.jar

ENTRYPOINT ["java", "-jar","karrigo.jar"]