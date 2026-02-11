package in.parida.fitness.aiservice;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class AiserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiserviceApplication.class, args);
	}



	@Bean
	public MongoClient mongoClient() {
		System.out.println("=== FORCING CORRECT MONGOCLIENT ===");
		String uri = "mongodb://localhost:27017/airecofitness";
		System.out.println("URI: " + uri);
		return MongoClients.create(uri);
	}

	@Bean
	public MongoTemplate mongoTemplate(MongoClient mongoClient) {
		MongoTemplate template = new MongoTemplate(mongoClient, "airecofitness");
		System.out.println("MongoTemplate connected to: " + template.getDb().getName());
		return template;
	}
}
