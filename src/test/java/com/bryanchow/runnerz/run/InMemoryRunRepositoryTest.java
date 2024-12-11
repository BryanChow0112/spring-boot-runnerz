package com.bryanchow.runnerz.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryRunRepositoryTest {

    InMemoryRunRepository repository;

    // The @BeforeEach annotation indicates that this method should be run before each test.
    // It is used to set up the test environment, ensuring a fresh state for each test.
    @BeforeEach
    void setup() {
        repository = new InMemoryRunRepository();
        repository.create(new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 10, Location.OUTDOOR, null));
        repository.create(new Run(2, "Evening Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 15, Location.INDOOR, null));
    }

    // The @Test annotation marks this method as a test case to be run by the JUnit framework.
    // Verifies that the findAll method returns the correct number of runs.
    @Test
    void shouldFindAllRuns() {
        List<Run> runs = repository.findAll();
        assertEquals(2, runs.size(), "Should have returned 2 runs");
    }

    // Verifies that the findById method returns the correct run with the specified ID.
    @Test
    void shouldFindRunWithValidId() {
        var run = repository.findById(1).get();
        assertEquals("Morning Run", run.title());
        assertEquals(10, run.kilometers());
    }

    // Verifies that the findById method throws a RunNotFoundException when an invalid ID is provided.
    @Test
    void shouldNotFindRunWithInvalidId() {
        RunNotFoundException notFoundException = assertThrows(
                RunNotFoundException.class,
                () -> repository.findById(3).get()
        );

        assertEquals("Run Not Found", notFoundException.getMessage());
    }

    // Verifies that the create method adds a new run to the repository.
    @Test
    void shouldCreateNewRun() {
        repository.create(new Run(3,
                "Friday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                3,
                Location.INDOOR, null));
        List<Run> runs = repository.findAll();
        assertEquals(3, runs.size());
    }

    // Verifies that the update method modifies an existing run in the repository.
    @Test
    void shouldUpdateRun() {
        repository.update(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                5,
                Location.OUTDOOR, null), 1);
        var run = repository.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(5, run.kilometers());
        assertEquals(Location.OUTDOOR, run.location());
    }

    // Verifies that the delete method removes a run from the repository.
    @Test
    void shouldDeleteRun() {
        repository.delete(1);
        List<Run> runs = repository.findAll();
        assertEquals(1, runs.size());
    }

}