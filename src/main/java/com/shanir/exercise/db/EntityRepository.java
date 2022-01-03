package com.shanir.exercise.db;

import com.shanir.exercise.model.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Collection;
import java.util.List;

public interface EntityRepository extends MongoRepository<Entity, String> {

    /**
     * Querying the db for entities that their offset is greater or equal than the given offset.
     * And Contain at least one topic from given topics.
     * Entities shall be returned in ascending order according to the offset.
     * @param offset
     * @param topics
     * @return entity list
     */
    @Query(value = "{ offset : { $gte: ?0 }, topics :{$elemMatch: {$in: ?1}}}", sort = "{offset:1}")
    List<Entity> getEntitiesByOffsetAndTopics(long offset, Collection<String> topics);
}
