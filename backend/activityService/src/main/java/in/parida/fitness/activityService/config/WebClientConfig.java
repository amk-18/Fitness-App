package in.parida.fitness.activityService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    //load balanced beacuse we will call service by name not url
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    //this will expose userservice as a webclient bean for this entire appliacation
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder.baseUrl("http://USERSERVICE")
                .build();
    }

}
