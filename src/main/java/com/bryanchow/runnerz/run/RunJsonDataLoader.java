package com.bryanchow.runnerz.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
    private final RunRepository runRepository;
    private final ObjectMapper objectMapper;

    public RunJsonDataLoader(RunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    // This method is called when the application starts
    @Override
    public void run(String... args) throws Exception {
        // Check if the run collection is empty
        if (runRepository.count() == 0) {
            // Load JSON data from the file
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {

                // Read and map JSON data from the input stream to the Runs object
                Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
                log.info("Reading {} runs from JSON data and saving to the database.", allRuns.runs().size());

                // Save all runs to the database
                runRepository.saveAll(allRuns.runs());

            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because the collection contains data.");
        }
    }
}
