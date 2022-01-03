package com.shanir.exercise.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Collection;

@Data
@Document(collection="entity")
public class Entity {
    @Id
    private String id;
    private long offset;
    private String payload;
    private Collection<String> topics;

    public Entity(long offset, String payload, Collection<String> topics) {
        this.offset = offset;
        this.payload = payload;
        this.topics = topics;
    }
}
