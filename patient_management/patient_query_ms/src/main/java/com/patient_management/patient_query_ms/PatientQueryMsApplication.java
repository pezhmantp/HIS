package com.patient_management.patient_query_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories({"com.patient_management.patient_core"})
@EntityScan(basePackages = {
		"com.patient_management.patient_core.model",
		"com.patient_management.patient_core.event",
		"com.patient_management.patient_core.shared",
		"org.axonframework.eventsourcing.eventstore.jpa",
		"org.axonframework.modelling.saga.repository.jpa",
		"org.axonframework.eventhandling.tokenstore"})
public class PatientQueryMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientQueryMsApplication.class, args);
	}

}
