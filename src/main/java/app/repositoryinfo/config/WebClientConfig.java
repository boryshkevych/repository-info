package app.repositoryinfo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient gitHubWebClient(@Value("${app.gitHubUrl}") String gitHubUrl) {
        return WebClient.create(gitHubUrl);
    }

}
