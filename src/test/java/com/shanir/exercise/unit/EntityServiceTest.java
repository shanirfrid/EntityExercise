package com.shanir.exercise.unit;

import com.shanir.exercise.db.EntityRepository;
import com.shanir.exercise.model.EntityFilter;
import com.shanir.exercise.service.EntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntityServiceTest {

    @Mock
    private EntityRepository entityRepository;
    private EntityService underTest;

    @BeforeEach
    void setUp() {
        underTest = new EntityService(entityRepository);
    }

    @Test
    void getEntitiesByEntityFilter() {
        //given
        Collection<String> topics = Arrays.asList("A","D");
        long offset = 7;

        // when
        underTest.getEntitiesByEntityFilter(new EntityFilter(offset,topics));

        // then
        ArgumentCaptor<Long> offsetArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Collection<String>> topicsArgumentCaptor = ArgumentCaptor.forClass(Collection.class);

        verify(entityRepository).
                getEntitiesByOffsetAndTopics(offsetArgumentCaptor.capture(),
                        topicsArgumentCaptor.capture());

        assertThat(offset).isEqualTo(offsetArgumentCaptor.getValue());
        assertThat(topics).isEqualTo(topicsArgumentCaptor.getValue());
    }
}
