package com.eniro.repository;

import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;
import com.eniro.model.SearchLogEntry;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 2015-04-19.
 */
public interface SearchHistoryRepo {
    void log(SearchLogEntry entry);

    void log(Country country, Map<Parameter, String> options, String... keywords);

    List<SearchLogEntry> list(long from,long to);


    void clear();
}
