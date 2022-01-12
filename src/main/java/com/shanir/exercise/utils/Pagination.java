package com.shanir.exercise.utils;

import org.springframework.stereotype.Component;

/***
 * Helper class for pages calculations.
 */
@Component
public class Pagination {
    public long getPrevious(int page){
        return (long) page - 1 < 0 ? 0 : page - 1;
    }
    public long getNext(long count, int page, int limit){
        return (long) (page + 1) * limit < count ? page + 1 : page;
    }
    public long getLast(long count, int limit){
        return (count - 1) / limit;
    }
}
