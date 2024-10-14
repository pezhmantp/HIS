package com.visit_management.visit_cmd_ms;



import com.visit_management.visit_cmd_ms.kafka.config.KafkaAdminClient;
import com.visit_management.visit_cmd_ms.kafka.config.KafkaConfigData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories({"com.visit_management.visit_core"})
@EntityScan(basePackages = {
		"com.visit_management.visit_core.model",
		"com.visit_management.visit_core.event",
		"com.visit_management.visit_core.query",
		"org.axonframework.eventsourcing.eventstore.jpa",
		"org.axonframework.eventhandling.saga.repository.jpa",
		"org.axonframework.eventhandling.tokenstore"
})
public class VisitCmdMsApplication implements CommandLineRunner {

	private final KafkaConfigData kafkaConfigData;

	private final KafkaAdminClient kafkaAdminClient;

	public VisitCmdMsApplication(KafkaConfigData kafkaConfigData, KafkaAdminClient kafkaAdminClient) {
		this.kafkaConfigData = kafkaConfigData;
		this.kafkaAdminClient = kafkaAdminClient;
	}


	public static void main(String[] args) {
		SpringApplication.run(VisitCmdMsApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate()
	{
		RestTemplate restTemplate = new RestTemplate();
		return new RestTemplate();
	}
	@Override
	public void run(String... args) throws Exception {
		kafkaAdminClient.createTopics();
	}
}
