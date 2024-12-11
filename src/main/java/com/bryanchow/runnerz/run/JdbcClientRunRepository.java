package com.bryanchow.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

// This annotation indicates that the class is a repository,
// which is used to encapsulate the logic required to access data sources.
@Repository
public class JdbcClientRunRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);

    // The JdbcClient object is a simple JDBC client that
    // provides a fluent API for interacting with a database.
    // It offers methods for setting SQL parameters, executing the SQL
    // and mapping the results to objects.
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        // Retrieve all runs and map the results to a list of Run objects
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id) {
        // Retrieve a run by its ID and map the result to an Optional<Run> object
        return jdbcClient.sql("SELECT id, title, started_on, completed_on, kilometers, location, version FROM run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        // Create a new run entry in the database
        var updated = jdbcClient.sql("INSERT INTO run (id, title, started_on, completed_on, kilometers, location) VALUES (?, ?, ?, ?, ?, ?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.kilometers(), run.location().toString()))
                .update();

        // Assert that one row was affected by the insert, otherwise throw an error
        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    public void update(Run run, Integer id) {
        // Modify an existing run entry in the database
        var updated = jdbcClient.sql("UPDATE run SET title = ?, started_on = ?, completed_on = ?, kilometers = ?, location = ? WHERE id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.kilometers(), run.location().toString(), id))
                .update();

        // Assert that one row was affected by the update, otherwise throw an error
        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    public void delete(Integer id) {
        // Remove a run entry from the database by its ID
        var updated = jdbcClient.sql("DELETE FROM run WHERE id = :id")
                .param("id", id)
                .update();

        // Assert that one row was affected by the delete, otherwise throw an error
        Assert.state(updated == 1, "Failed to delete run " + id);
    }

    public int count() {
        // Count the number of rows in the run table
        return jdbcClient.sql("SELECT * FROM run").query().listOfRows().size();
    }

    public void saveAll(List<Run> runs) {
        // Iterate through the list of runs and create each one in the database
        runs.stream().forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        // Retrieve runs by location and map the results to a list of Run objects
        return jdbcClient.sql("SELECT * FROM run WHERE location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }
}
