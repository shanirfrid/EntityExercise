package com.shanir.exercise.utils;

import com.shanir.exercise.model.Entity;
import com.shanir.exercise.model.EntityProto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Conversions {

    public EntityProto.Entity entityToProtoEntity(Entity entity){
        return EntityProto.Entity.newBuilder().setOffset(entity.getOffset())
                .setPayload(entity.getPayload())
                .addAllTopics(entity.getTopics()).build();
    }

    public List<EntityProto.Entity> entityListToProtoEntityList(List<Entity> entityList){
        return entityList.stream()
                .map(this::entityToProtoEntity)
                .collect(Collectors.toList());

    }

    public EntityProto.Entities protoListToProtoEntities(List<Entity> entityList, long previous, long next, long last) {
        return EntityProto.Entities
                .newBuilder()
                .addAllEntity(entityListToProtoEntityList(entityList))
                .setPrevious(previous)
                .setNext(next)
                .setLast(last)
                .build();
    }




}
