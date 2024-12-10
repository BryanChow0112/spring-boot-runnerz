package com.bryanchow.runnerz.user;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

// Marks this class as a Spring component, making it eligible for component scanning and dependency injection.
@Component
public class UserRestClient {

    // RestClient is a Spring class used for making RESTful web service calls.
    // It simplifies the process of sending HTTP requests and processing HTTP responses.
    private final RestClient restClient;

    /**
     * Constructor for UserRestClient.
     * The RestClient.Builder is used to create an instance of RestClient with a base URL.
     *
     * @param builder the RestClient.Builder instance used to build the RestClient
     */
    public UserRestClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")  // Sets the base URL for the RestClient
                .build();  // Builds the RestClient instance
    }

    /**
     * Fetches a list of all users from the external service.
     *
     * @return a list of User objects
     */
    public List<User> findAll() {
        // Initiates a GET request to the "/users" endpoint
        return restClient.get()
                .uri("/users") // Specifies the URI for the request
                .retrieve() // Executes the request and retrieves the response
                .body(new ParameterizedTypeReference<>() {}); // Deserializes the response body into a List<User>
    }

    /**
     * Fetches a user by their ID from the external service.
     *
     * @param id the ID of the user to fetch
     * @return the User object corresponding to the given ID
     */
    public User findById(Integer id) {
        // Initiates a GET request to the "/users/{id}" endpoint with a path variable
        return restClient.get()
                .uri("/users/{id}", id) // Specifies the URI with a path variable for the user ID
                .retrieve() // Executes the request and retrieves the response
                .body(User.class); // Deserializes the response body into a User object
    }
}
