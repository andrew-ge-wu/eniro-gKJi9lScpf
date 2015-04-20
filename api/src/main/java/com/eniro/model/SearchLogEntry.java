package com.eniro.model;

import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;

import java.util.Date;
import java.util.Map;

/**
 * Created by andrew on 2015-04-19.
 */
public class SearchLogEntry implements Comparable<SearchLogEntry> {
    private final long searchDate;
    private final Country country;
    private final Map<Parameter, String> options;
    private final String[] keywords;

    public SearchLogEntry(Country country, Map<Parameter, String> options, String... keywords) {
        this(System.currentTimeMillis(), country, options, keywords);
    }

    public SearchLogEntry(long timestamp, Country country, Map<Parameter, String> options, String... keywords) {
        this.searchDate = timestamp;
        this.country = country;
        this.options = options;
        this.keywords = keywords;
    }

    public long getSearchDate() {
        return searchDate;
    }

    public Country getCountry() {
        return country;
    }

    public Map<Parameter, String> getOptions() {
        return options;
    }

    public String[] getKeywords() {
        return keywords;
    }

    @Override
    public int compareTo(SearchLogEntry entry) {
        return (int) (entry.getSearchDate() - getSearchDate());
    }
}
