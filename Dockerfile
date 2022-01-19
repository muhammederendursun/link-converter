FROM adoptopenjdk:11-jre-openj9

ARG JAR_FILE="target/linkConverter-1.0.0-SNAPSHOT.jar"

COPY ${JAR_FILE} link-converter.jar

CMD ["java", "-jar", "link-converter.jar"]