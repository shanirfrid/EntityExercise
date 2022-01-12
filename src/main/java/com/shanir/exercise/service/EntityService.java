package com.shanir.exercise.service;

import com.shanir.exercise.db.EntityRepository;
import com.shanir.exercise.model.Entity;
import com.shanir.exercise.model.EntityFilter;
import com.shanir.exercise.model.EntityProto;
import com.shanir.exercise.utils.Conversions;
import com.shanir.exercise.utils.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EntityService {
    @Autowired private final Conversions conversions;
    @Autowired private final Pagination pagination;
    private final EntityRepository entityRepository;

    /***
     * Retrieves limit number of entities in the given page (offset) sorted
     * in ascending order according to their offset.
     * Additionally, returns the previous page, the next page and the last page.
     */
    public Mono<EntityProto.Entities> getEntitiesByEntityFilter(EntityFilter entityFilter,
                                                                int page, int limit) {
        return this.entityRepository.count()
                .flatMap(count ->
                        this.entityRepository
                                .getEntitiesByOffsetAndTopics(entityFilter.getOffset(),
                                        entityFilter.getTopics(),
                                        PageRequest.of(page, limit, Sort.by("offset")))
                                    .collectList()
                                    .map(e -> conversions.protoListToProtoEntities(e,
                                            pagination.getPrevious(page),
                                            pagination.getNext(count, page,limit),
                                            pagination.getLast(count, limit)))
                );
    }

    public Mono<Entity> saveEntity(Entity entity) {
        return this.entityRepository.save(entity);
    }
}
