package com.shanir.exercise.web.rest;

import com.shanir.exercise.model.Entity;
import com.shanir.exercise.model.EntityProto;
import com.shanir.exercise.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EntityController {
    private final EntityService entityService;

    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @PostMapping("/addEntity")
    public Entity addEntity(@RequestBody Entity entity){
        return this.entityService.saveEntity(entity);
    }

    @GetMapping("/getEntities")
    public EntityProto.Entities getEntities(){
        return this.entityService.getEntities();
    }
}
