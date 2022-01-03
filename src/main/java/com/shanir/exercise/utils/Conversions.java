package com.shanir.exercise.utils;

import com.shanir.exercise.model.Entity;
import com.shanir.exercise.model.EntityProto;
import java.util.ArrayList;
import java.util.List;

public class Conversions {

    public static EntityProto.Entity entityToProtoEntity(Entity entity){
        return EntityProto.Entity.newBuilder().setOffset(entity.getOffset())
                .setPayload(entity.getPayload())
                .addAllTopics(entity.getTopics()).build();
    }

    public static List<EntityProto.Entity> entityListToProtoEntityList(List<Entity> entityList){
        List<EntityProto.Entity> entityProtoList = new ArrayList<>();
        for (Entity e: entityList) {
            entityProtoList.add(Conversions.entityToProtoEntity(e));
        }
        return entityProtoList;
    }
}
