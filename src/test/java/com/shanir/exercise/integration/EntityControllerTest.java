package com.shanir.exercise.integration;

import com.shanir.exercise.db.EntityRepository;
import com.shanir.exercise.model.Entity;
import com.shanir.exercise.model.EntityFilter;
import com.shanir.exercise.model.EntityProto;
import com.shanir.exercise.service.EntityService;
import com.shanir.exercise.utils.Conversions;
import com.shanir.exercise.utils.Pagination;
import com.shanir.exercise.web.rest.EntityController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(EntityController.class)
@Import({Conversions.class, Pagination.class})
class EntityControllerTest {

    @Autowired WebTestClient webTestClient;
    @Autowired Conversions conversions;
    @Autowired  Pagination pagination;

    @MockBean
    EntityService entityService;

    private static final String URL = "http://localhost:8081/fetchEntities";

    @Test
    void getEntitiesByEntityFilter() throws Exception {
        Collection<String> topics = Arrays.asList("A","D");
        long count = 4, offset = 8;
        int limit = 2, page = 0;

        List<Entity> entitiesToAdd = Arrays.asList(
                new Entity(12,"25",Arrays.asList("A","B")),
                new Entity(4,"26",Arrays.asList("C","B")),
                new Entity(9,"27",Arrays.asList("D")),
                new Entity(2,"28",Arrays.asList("C","B"))
        );

        List<Entity> expectedEntities = Arrays.asList(
                new Entity(9,"27",Arrays.asList("D")),
                new Entity(12,"25",Arrays.asList("A","B"))
        );

        entitiesToAdd.forEach(entity -> this.entityService.saveEntity(entity));
        EntityFilter entityFilter = new EntityFilter(offset,topics);

        Mono<EntityProto.Entities> entitiesMockResult = Mono.just(
                conversions.protoListToProtoEntities(expectedEntities,
                        pagination.getPrevious(page),pagination.getNext(count,page,limit),
                        pagination.getLast(count,limit)
                        ));

        when(entityService.getEntitiesByEntityFilter(entityFilter, page, limit))
                .thenReturn(entitiesMockResult);

        webTestClient.post()
                .uri(String.format(URL + "/?page=%d&limit=%d", page, limit))
                .bodyValue(entityFilter)
                .exchange()
                .expectStatus().isOk();
    }
}
