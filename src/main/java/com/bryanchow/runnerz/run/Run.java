package com.bryanchow.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

public record Run(
        // Marks this field as the identifier for the entity.
        // It will be used as the primary key in the database.
        @Id
        Integer id,
        @NotEmpty
        String title,
        LocalDateTime startedOn,
        LocalDateTime completedOn,
        @Positive
        Integer kilometers,
        Location location,
        // Marks this field as the version field for optimistic locking.
        // It will be used to manage concurrent updates to the entity.
        @Version
        Integer version
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
