package com.bryanchow.runnerz.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

/**
 * UserHttpClient is an interface that defines methods for interacting
 * with a RESTful web service to perform operations related to User entities.
 *
 * It uses Spring's HTTP client annotations to declaratively
 * specify the endpoints and HTTP methods.
 */
public interface UserHttpClient {

    /**
     * Fetches all users from the /users endpoint.
     *
     * @return a list of User objects
     */
    // @GetExchange specifies this method performs a GET request to the /users endpoint
    @GetExchange("/users")
    List<User> findAll();

    /**
     * Fetches a user by their ID from the /users/{id} endpoint.
     *
     * @param id the ID of the user to fetch
     * @return the User object corresponding to the given ID
     */
    @GetExchange("/users/{id}")
    User findById(@PathVariable Integer id);
}
