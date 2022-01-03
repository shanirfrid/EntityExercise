package com.shanir.exercise.service;

import com.shanir.exercise.db.EntityRepository;
import com.shanir.exercise.model.Entity;
import com.shanir.exercise.model.EntityProto;
import com.shanir.exercise.utils.Conversions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntityService {
    private final EntityRepository entityRepository;

    public Entity saveEntity(Entity entity) {
        return this.entityRepository.save(entity);
    }

    public EntityProto.Entities getEntities() {
        return EntityProto.Entities.newBuilder()
                .addAllEntity(Conversions.entityListToProtoEntityList
                        (this.entityRepository.findAll()))
                .build();
    }
}
