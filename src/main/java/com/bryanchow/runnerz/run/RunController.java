package com.bryanchow.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// RestController annotation indicates that this class serves as a RESTful web service controller.
@RestController
// RequestMapping annotation maps HTTP requests to handler methods of MVC and REST controllers.
// This maps HTTP requests directed to /api/runs to this controller.
@RequestMapping("/api/runs")
public class RunController {

    // The repository instance to perform CRUD operations on Run entities
    private final RunRepository runRepository;

    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    /**
     * @GetMapping("") annotation maps HTTP GET requests to the findAll method.
     * It returns a list of all runs in the repository.
     */
    @GetMapping("")
    List<Run> findAll() {
        return runRepository.findAll();
    }

    /**
     * @GetMapping("/{id}") annotation maps HTTP GET requests to the findById method.
     * @PathVariable annotation binds the method parameter to a URI template variable.
     * It returns a run by its ID.
     * If the run is not found, it throws a RunNotFoundException.
     */
    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {

        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new RunNotFoundException();
        }
        return run.get();
    }

    /**
     * @ResponseStatus(HttpStatus.CREATED) annotation sets the HTTP response status to 201 Created.
     * @PostMapping("") annotation maps HTTP POST requests to the create method.
     * @Valid annotation ensures that the request body is validated.
     * @RequestBody annotation binds the HTTP request body to the method parameter.
     * It saves a new run to the repository.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run) {
        runRepository.save(run);
    }

    /**
     * @ResponseStatus(HttpStatus.NO_CONTENT) annotation sets the HTTP response status to 204 No Content.
     * @PutMapping("/{id}") annotation maps HTTP PUT requests to the update method.
     * @Valid annotation ensures that the request body is validated.
     * @RequestBody annotation binds the HTTP request body to the method parameter.
     * It updates an existing run in the repository.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runRepository.save(run);
    }

    /**
     * @ResponseStatus(HttpStatus.NO_CONTENT) annotation sets the HTTP response status to 204 No Content.
     * @DeleteMapping("/{id}") annotation maps HTTP DELETE requests to the delete method.
     * It deletes a run from the repository by its ID.
     * If the run is not found, it throws a RunNotFoundException.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        runRepository.delete(runRepository.findById(id).get());
    }

    /**
     * @GetMapping("/location/{location}") annotation maps HTTP GET requests to the findAllByLocation method.
     * It returns a list of runs filtered by location.
     */
    @GetMapping("/location/{location}")
    List<Run> findAllByLocation(@PathVariable String location) {
        return runRepository.findAllByLocation(location);
    }
}
