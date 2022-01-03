package com.shanir.exercise.model;

import lombok.Data;
import java.util.Collection;

/**
 * This class was created in order to help us to capture parameters passed in the fetch
 * entities post request.
 */
@Data
public class EntityFilter {
    private long offset;
    private Collection<String> topics;

    public EntityFilter(long offset, Collection<String> topics) {
        this.offset = offset;
        this.topics = topics;
    }
}
