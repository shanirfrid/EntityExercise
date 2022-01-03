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

    /*
    Overriding equals method helps us comparing two entities by
    ignoring their Id field value which is not important in entity comparison.
     */
    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Entity)) return false;
        Entity e = (Entity) other;

        return this.getOffset() == e.getOffset() &&
                this.getTopics().equals(e.getTopics()) &&
                this.getPayload().equals(e.getPayload());
    }
}
