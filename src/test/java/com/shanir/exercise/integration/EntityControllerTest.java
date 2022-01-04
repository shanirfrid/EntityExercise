package com.shanir.exercise.integration;

import com.google.gson.Gson;
import com.shanir.exercise.model.Entity;
import com.shanir.exercise.model.EntityFilter;
import com.shanir.exercise.model.EntityProto;
import com.shanir.exercise.service.EntityService;
import com.shanir.exercise.utils.Conversions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EntityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    EntityService entityService;

    private static final String URL = "http://localhost:8081/fetchEntities";

    @Test
    void getEntitiesByEntityFilter() throws Exception {
        Collection<String> topics = Arrays.asList("A","D");
        long offset = 8;

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

        for (Entity entity:entitiesToAdd) {
            this.entityService.saveEntity(entity);
        }

        EntityFilter entityFilter = new EntityFilter(offset,topics);

        EntityProto.Entities entitiesMockResult = EntityProto.Entities.newBuilder()
                .addAllEntity(Conversions.entityListToProtoEntityList(expectedEntities)).build();

        RequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .content(new Gson().toJson(entityFilter))
                .contentType(MediaType.APPLICATION_JSON);

        when(entityService.getEntitiesByEntityFilter(entityFilter))
                .thenReturn(entitiesMockResult);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content()
                 .contentType("application/x-protobuf;charset=UTF-8")).andReturn();
    }
}
