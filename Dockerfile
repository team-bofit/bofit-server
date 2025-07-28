FROM eclipse-temurin:17.0.9_9-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES:-dev-blue}", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
