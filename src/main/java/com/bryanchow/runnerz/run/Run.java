package com.bryanchow.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Run(
        Integer id,
        @NotEmpty
        String title,
        LocalDateTime startedOn,
        LocalDateTime completedOn,
        @Positive
        Integer kilometers,
        Location location
) {

    public Run {
        // We can perform error validation here or using the @Valid annotation
        // in the controller which is from the jakarta.validation.constraints package
        // which needs to be added to the pom.xml file as a dependency
        if (!completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("Run must be completed after it has started");
        }
    }
}
