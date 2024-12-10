package com.bryanchow.runnerz;

import com.bryanchow.runnerz.user.UserHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Main class for the Spring Boot application.
 *
 * This class is responsible for bootstrapping the application,
 * configuring beans, and setting up the application context.
 */
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Bean definition for UserHttpClient.
     *
     * This method creates and configures a UserHttpClient bean
     * to interact with the JSONPlaceholder API.
     *
     * @return an instance of UserHttpClient
     */
    @Bean
    UserHttpClient userHttpClient() {
        // Create a RestClient instance with the base URL for JSONPlaceholder API
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");

        // Create a proxy factory for creating HTTP service clients
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();

        // Use the factory to create an implementation of the UserHttpClient interface
        return factory.createClient(UserHttpClient.class);
    }
}
