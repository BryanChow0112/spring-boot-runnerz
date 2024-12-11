package com.bryanchow.runnerz.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest annotation indicates that the tests are running with the full Spring Boot context
// The webEnvironment attribute is set to RANDOM_PORT to start the application with a random port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RunControllerIntTest {

    // The random port for the test server injected by Spring Boot
    @LocalServerPort
    int randomServerPort;

    // RestClient used for making HTTP requests to the local server
    RestClient restClient;

    @BeforeEach
    void setup() {
        restClient = RestClient.create("http://localhost:" + randomServerPort);
    }

    // Verify the endpoint for finding all runs returns the expected list of runs
    @Test
    void shouldFindAllRuns() {
        // Perform a GET request to /api/runs/ to retrieve all runs
        List<Run> runs = restClient.get()
                .uri("/api/runs")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assertEquals(10, runs.size());
    }

    // Verify the endpoint for finding a run by ID returns the expected run
    @Test
    void shouldFindRunById() {
        // Perform a GET request to /api/runs/1 to retrieve the run with ID 1
        Run run = restClient.get()
                .uri("/api/runs/1")
                .retrieve()
                .body(Run.class);

        // Assert that the run with ID 1 has the expected attributes
        assertAll(
                () -> assertEquals(1, run.id()),
                () -> assertEquals("Noon Run", run.title()),
                () -> assertEquals("2024-02-20T06:05", run.startedOn().toString()),
                () -> assertEquals("2024-02-20T10:27", run.completedOn().toString()),
                () -> assertEquals(24, run.kilometers()),
                () -> assertEquals(Location.INDOOR, run.location()));
    }

    // Verify the endpoint for creating a new run returns a 201 Created status
    @Test
    void shouldCreateNewRun() {
        Run run = new Run(11, "Evening Run", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 10, Location.OUTDOOR, null);

        // Perform a POST request to /api/runs to create a new run
        ResponseEntity<Void> newRun = restClient.post()
                .uri("/api/runs")
                .body(run)
                .retrieve()
                .toBodilessEntity();

        assertEquals(201, newRun.getStatusCodeValue());
    }

    // Verify the endpoint for updating an existing run returns a 204 No Content status
    @Test
    void shouldUpdateExistingRun() {
        // Perform a GET request to /api/runs/1 to retrieve the run with ID 1
        Run run = restClient.get()
                .uri("/api/runs/1")
                .retrieve()
                .body(Run.class);

        // Perform a PUT request to update the run with ID 1
        ResponseEntity<Void> updatedRun = restClient.put()
                .uri("/api/runs/1")
                .body(run)
                .retrieve()
                .toBodilessEntity();  // Retrieve the response as a Void entity

        assertEquals(204, updatedRun.getStatusCodeValue());
    }

    // Verify the endpoint for deleting a run returns a 204 No Content status
    @Test
    void shouldDeleteRun() {
        // Perform a DELETE request to delete the run with ID 1
        ResponseEntity<Void> run = restClient.delete()
                .uri("/api/runs/1")
                .retrieve()
                .toBodilessEntity();

        assertEquals(204, run.getStatusCodeValue());
    }
}