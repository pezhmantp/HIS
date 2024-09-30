FROM openjdk:17-jdk-slim
COPY patient_management/patient_cmd_ms/target/patient_cmd_ms-1.0.jar patient_cmd_ms-1.0.jar
ENTRYPOINT ["java","-jar","/patient_cmd_ms-1.0.jar"]