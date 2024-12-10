package com.bryanchow.runnerz.run;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

/**
 * RunRepository interface for performing CRUD operations on Run entities.
 * Extends Spring Data's ListCrudRepository to utilise automatic implementation
 * of standard CRUD methods.
 */
public interface RunRepository extends ListCrudRepository<Run, Integer> {

    /**
     * Find all runs by location.
     *
     * @param location the location to search for
     * @return a list of runs with the specified location
     */
    List<Run> findAllByLocation(String location);
}
