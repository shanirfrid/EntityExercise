package com.shanir.exercise.unit;

import com.shanir.exercise.db.EntityRepository;
import com.shanir.exercise.model.Entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        List<Entity> entitiesToAdd = Arrays.asList(
                new Entity(12,"25",Arrays.asList("A","B")),
                new Entity(4,"26",Arrays.asList("C","B")),
                new Entity(9,"27",Arrays.asList("D")),
                new Entity(2,"28",Arrays.asList("C","B"))
                );

        for (Entity entity:entitiesToAdd) {
            this.underTest.save(entity);
        }

        long offset = 2;
        Collection<String> topics = Arrays.asList("B");

        //when
        List<Entity> returnedEntities =
                underTest.getEntitiesByOffsetAndTopics(offset, topics);

        //then
        List<Entity> expectedEntities = Arrays.asList(
                new Entity(2,"28",Arrays.asList("C","B")),
                new Entity(4,"26",Arrays.asList("C","B")),
                new Entity(12,"25",Arrays.asList("A","B"))
        );

        assertThat(expectedEntities).isEqualTo(returnedEntities);
    }
}
