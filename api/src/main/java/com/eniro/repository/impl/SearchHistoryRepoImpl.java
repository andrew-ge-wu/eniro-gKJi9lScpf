package com.eniro.repository.impl;

import com.eniro.Constants;
import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;
import com.eniro.model.SearchLogEntry;
import com.eniro.repository.SearchHistoryRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andrew on 2015-04-19.
 */
@Component
@ConfigurationProperties
public class SearchHistoryRepoImpl implements SearchHistoryRepo {
    private final Map<Long, List<SearchLogEntry>> storage = new ConcurrentHashMap<>();

    @Override
    public void log(SearchLogEntry entry) {
        long partition = getPartition(entry.getSearchDate());
        if (!storage.containsKey(partition))
            storage.put(partition, Collections.synchronizedList(new ArrayList<SearchLogEntry>()));
        storage.get(partition).add(0, entry);
    }

    @Override
    public void log(Country country, Map<Parameter, String> options, String... keywords) {
        log(new SearchLogEntry(country, options, keywords));
    }


    @Override
    public List<SearchLogEntry> list(long from, long to) {
        long fromPartition = getPartition(from);
        long toPartition = getPartition(to);
        List<SearchLogEntry> toReturn = new ArrayList<>();
        for (long i = fromPartition + 1; i < toPartition; i++) {
            if (storage.containsKey(i)) {
                toReturn.addAll(storage.get(i));
            }
        }
        if (fromPartition != toPartition) toReturn.addAll(selectRange(storage.get(fromPartition), from, to));
        toReturn.addAll(selectRange(storage.get(toPartition), from, to));
        Collections.sort(toReturn);
        return toReturn;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    protected List<SearchLogEntry> selectRange(List<SearchLogEntry> source, long from, long to) {
        if (source != null && source.size() > 0) {
            Collections.sort(source);
            int tillIdx = lookupToIdx(source, from, source.size() - 1);
            int fromIdx = lookupFromIdx(source,to,0);
            if(fromIdx == source.size()||tillIdx == -1){
                return new ArrayList<>();
            }else {
                return source.subList(fromIdx, tillIdx + 1);
            }
        } else {
            return new ArrayList<>();
        }
    }

    private int lookupFromIdx(List<SearchLogEntry> source, long to, int fromIdx) {
        if (fromIdx < source.size()) {
            if (source.get(fromIdx).getSearchDate() > to) {
                return lookupFromIdx(source, to, fromIdx + 1);
            }
        }
        return fromIdx;
    }

    private int lookupToIdx(List<SearchLogEntry> source, long from, int tillIdx) {
        if (tillIdx != -1) {
            if (source.get(tillIdx).getSearchDate() < from) {
                return lookupToIdx(source, from, tillIdx - 1);
            }
        }
        return tillIdx;
    }

    private long getPartition(long timestamp) {
        return timestamp / Constants.LOG_PARTITION_PERIOD;
    }
}
