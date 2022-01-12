package com.shanir.exercise.web.rest;

import com.shanir.exercise.model.Entity;
import com.shanir.exercise.model.EntityFilter;
import com.shanir.exercise.model.EntityProto;
import com.shanir.exercise.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class EntityController {
    private final EntityService entityService;

    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @PostMapping("/fetchEntities")
    public Mono<ResponseEntity<EntityProto.Entities>> getEntitiesByEntityFilter
            (@RequestBody EntityFilter entityFilter, @RequestParam int page,
             @RequestParam int limit) {
        return this.entityService.getEntitiesByEntityFilter(entityFilter,page,limit)
                .map(e -> new ResponseEntity<>(e,HttpStatus.OK));
    }

    @PostMapping("/addEntity")
    public Mono<Entity> addEntity(@RequestBody Entity entity){
        return this.entityService.saveEntity(entity);
    }
}
