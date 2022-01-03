package com.shanir.exercise.db;

import com.shanir.exercise.model.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntityRepository extends MongoRepository<Entity, String> {
}
