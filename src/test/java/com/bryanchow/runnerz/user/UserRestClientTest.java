package com.bryanchow.runnerz.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

// RestClientTest annotation is used to test REST clients.
@RestClientTest(UserRestClient.class)
class UserRestClientTest {

    @Autowired
    // Mock server to simulate REST API responses
    MockRestServiceServer server;

    @Autowired
    // The REST client being tested
    UserRestClient client;

    @Autowired
    // ObjectMapper for serializing and deserializing JSON
    ObjectMapper objectMapper;

    // Verify that the client can fetch all users from the external service
    @Test
    void shouldFindAllUsers() throws JsonProcessingException {
        // given
        // Create a sample user
        User user1 = new User(1,
                "Leanne",
                "lgraham",
                "lgraham@gmail.com",
                new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", new Geo(-37.3159, 81.1496)),
                "1-770-736-8031 x56442",
                "hildegard.org",
                new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"));

        // Create a list of users containing the sample user
        List<User> users = List.of(user1);

        // when
        // Set up the mock server to expect a request to the given URL and respond with the sample user list
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(users), MediaType.APPLICATION_JSON));

        // then
        // Call the client's findAll method and verify the response matches the expected list of users
        List<User> allUsers = client.findAll();
        assertEquals(users, allUsers);
    }

    // Verify that the client can fetch a user by ID from the external service
    @Test
    void shouldFindUserById() throws JsonProcessingException {
        // given
        // Create a sample user
        User user = new User(1,
                "Leanne",
                "lgraham",
                "lgraham@gmail.com",
                new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", new Geo(-37.3159, 81.1496)),
                "1-770-736-8031 x56442",
                "hildegard.org",
                new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"));

        // when
        // Set up the mock server to expect a request to the given URL and respond with the sample user
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users/1"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(user), MediaType.APPLICATION_JSON));

        // then
        // Call the client's findById method and verify the response matches the expected user details
        User leanne = client.findById(1);
        assertEquals("Leanne", leanne.name(), "User name should be Leanne");
        assertEquals("lgraham", leanne.username(), "User username should be lgraham");
        assertEquals("lgraham@gmail.com", leanne.email());
        assertAll("Address",
                () -> assertEquals("Kulas Light", leanne.address().street()),
                () -> assertEquals("Apt. 556", leanne.address().suite()),
                () -> assertEquals("Gwenborough", leanne.address().city()),
                () -> assertEquals("92998-3874", leanne.address().zipcode()),
                () -> assertEquals(-37.3159, leanne.address().geo().lat()),
                () -> assertEquals(81.1496, leanne.address().geo().lng())
        );
        assertEquals("1-770-736-8031 x56442", leanne.phone());
        assertEquals("hildegard.org", leanne.website());
        assertAll("Company",
                () -> assertEquals("Romaguera-Crona", leanne.company().name()),
                () -> assertEquals("Multi-layered client-server neural-net", leanne.company().catchPhrase()),
                () -> assertEquals("harness real-time e-markets", leanne.company().bs()));
    }

}