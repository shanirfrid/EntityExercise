package com.shanir.exercise.service;

import com.shanir.exercise.db.EntityRepository;
import com.shanir.exercise.model.Entity;
import com.shanir.exercise.model.EntityFilter;
import com.shanir.exercise.model.EntityProto;
import com.shanir.exercise.utils.Conversions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntityService {
    private final EntityRepository entityRepository;

    public EntityProto.Entities getEntitiesByEntityFilter(EntityFilter entityFilter) {
        return EntityProto.Entities.newBuilder()
                .addAllEntity(Conversions.entityListToProtoEntityList(
                        this.entityRepository.getEntitiesByOffsetAndTopics
                                (entityFilter.getOffset(), entityFilter.getTopics())))
                .build();
    }

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
