package com.shanir.exercise.db;

import com.shanir.exercise.model.Entity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;
import java.util.Collection;

public interface EntityRepository extends ReactiveMongoRepository<Entity, String> {

    /**
     * Querying the db for entities that their offset is greater or equal than the given offset.
     * And Contain at least one topic from given topics.
     * @param offset
     * @param topics
     * @return entity list
     */
    @Query(value = "{ offset : { $gte: ?0 }, topics :{$elemMatch: {$in: ?1}}}")
    Flux<Entity> getEntitiesByOffsetAndTopics(long offset, Collection<String> topics,
                                              Pageable pageable);
}
