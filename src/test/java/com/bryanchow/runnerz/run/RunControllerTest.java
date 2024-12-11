package com.bryanchow.runnerz.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// WebMvcTest annotation is used to test Spring MVC controllers.
@WebMvcTest(RunController.class)
class RunControllerTest {

    // MockMvc is used to perform HTTP requests in tests
    @Autowired
    MockMvc mvc;

    // ObjectMapper is used to serialize and deserialize objects to and from JSON.
    @Autowired
    ObjectMapper objectMapper;

    // MockitoBean is used to create a mock implementation of RunRepository
    @MockitoBean
    RunRepository repository;

    // List of runs to be used in tests
    private final List<Run> runs = new ArrayList<>();

    // Initialize the test data before each test
    @BeforeEach
    void setUp() {
        runs.add(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                3,
                Location.INDOOR, null));
    }

    // Verify that the findAll method returns all runs
    @Test
    void shouldFindAllRuns() throws Exception {
        // When the findAll method is called, it will return the list of runs
        when(repository.findAll()).thenReturn(runs);

        // Perform a GET request to /api/runs and validate the response
        mvc.perform(get("/api/runs"))
                .andExpect(status().isOk())  // Verify that the status code is 200 OK
                .andExpect(jsonPath("$.size()", is(runs.size())));  // Verify that the response contains all runs
    }

    // Verify that the findById method returns a run by its ID
    @Test
    void shouldFindOneRun() throws Exception {
        Run run = runs.getFirst();  // get the first run
        // When the findById method is called with any integer argument, it will return the run
        when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(run));

        // Perform a GET request to /api/runs/1 and validate the response
        mvc.perform(get("/api/runs/1"))
                .andExpect(status().isOk())
                // Verify that the response contains the correct run details
                .andExpect(jsonPath("$.id", is(run.id())))
                .andExpect(jsonPath("$.title", is(run.title())))
                .andExpect(jsonPath("$.kilometers", is(run.kilometers())))
                .andExpect(jsonPath("$.location", is(run.location().toString())));
    }

    // Verify that a 404 Not Found status is returned when an invalid ID is provided
    @Test
    void shouldReturnNotFoundWithInvalidId() throws Exception {
        // Perform a GET request to /api/runs/99 and verify that the status code is 404 Not Found
        mvc.perform(get("/api/runs/99"))
                .andExpect(status().isNotFound());
    }

    // Verify that a new run is created successfully
    @Test
    void shouldCreateNewRun() throws Exception {
        var run = new Run(null, "test", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 1, Location.INDOOR, null);

        // Perform a POST request to /api/runs with the run object as the request body
        mvc.perform(post("/api/runs")
                        .contentType(MediaType.APPLICATION_JSON)  // Set the content type to JSON
                        .content(objectMapper.writeValueAsString(run))  // Convert the run object to JSON
                )
                .andExpect(status().isCreated());  // Verify that the status code is 201 Created
    }

    // Verify that an existing run is updated successfully
    @Test
    void shouldUpdateRun() throws Exception {
        var run = new Run(null, "test", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 1, Location.INDOOR, null);

        // Perform a PUT request to /api/runs/1 with the updated run object as the request body
        mvc.perform(put("/api/runs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(run))
                )
                .andExpect(status().isNoContent());
    }

    // Verify that a run is deleted successfully
    @Test
    public void shouldDeleteRun() throws Exception {
        Run run = runs.getFirst();

        // When the findById method is called with any integer argument, it will return the run
        when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(run));

        // Perform a DELETE request to /api/runs/1 and verify that the status code is 204 No Content
        mvc.perform(delete("/api/runs/1"))
                .andExpect(status().isNoContent());
    }
}