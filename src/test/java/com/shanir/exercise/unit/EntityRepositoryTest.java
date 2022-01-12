package com.shanir.exercise.unit;

import com.shanir.exercise.db.EntityRepository;
import com.shanir.exercise.model.Entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@DataMongoTest
class EntityRepositoryTest {

    @Autowired
    private EntityRepository underTest;

    @AfterEach
    public void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckEntitiesByOffsetAndTopics() {
        //given
        int page = 0, limit = 1;
        long offset = 2;
        List<Entity> entitiesToAdd = Arrays.asList(
                new Entity(12,"25",Arrays.asList("A","B")),
                new Entity(4,"26",Arrays.asList("C","B")),
                new Entity(9,"27",Arrays.asList("D")),
                new Entity(2,"28",Arrays.asList("C","B"))
                );

        for (Entity entity:entitiesToAdd) {
            this.underTest.save(entity).block();
        }

        Collection<String> topics = Arrays.asList("C");

        //when
        Flux<Entity> returnedEntities =
                underTest.getEntitiesByOffsetAndTopics(offset, topics,
                        PageRequest.of(page, limit, Sort.by("offset")));
        //then
        Entity expectedEntity =
                new Entity(4,"26",Arrays.asList("C","B"));

        Flux<Entity> entitiesFlux = underTest.getEntitiesByOffsetAndTopics(offset, topics,
                PageRequest.of(page, limit, Sort.by("offset")));

        StepVerifier.create(entitiesFlux)
                .assertNext(entity -> {
                    entity.equals(expectedEntity);
                })
                .expectComplete()
                .verify();
    }
}
