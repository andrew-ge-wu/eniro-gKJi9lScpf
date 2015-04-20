package com.eniro.api.model;

import java.util.List;

/**
 * Created by andrew on 2015-04-18.
 */
public class SearchResult {
    private String title;

    private String startIndex;

    private String totalCount;

    private String totalHits;

    private String query;

    private List<Adverts> adverts;

    private String itemsPerPage;

    private SearchResult() {
    }

    public String getTitle() {
        return title;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public String getTotalHits() {
        return totalHits;
    }

    public String getQuery() {
        return query;
    }

    public List<Adverts> getAdverts() {
        return adverts;
    }

    public String getItemsPerPage() {
        return itemsPerPage;
    }
}
