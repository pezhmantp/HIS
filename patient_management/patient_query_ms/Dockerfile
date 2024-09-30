FROM openjdk:17-jdk-slim
COPY patient_management/patient_query_ms/target/patient_query_ms-1.0.jar patient_query_ms-1.0.jar
ENTRYPOINT ["java","-jar","/patient_query_ms-1.0.jar"]