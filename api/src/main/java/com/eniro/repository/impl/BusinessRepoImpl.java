package com.eniro.repository.impl;

import com.eniro.api.BasicSearchClient;
import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;
import com.eniro.api.exception.AssertException;
import com.eniro.api.model.Adverts;
import com.eniro.api.model.SearchResult;
import com.eniro.repository.BusinessRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by andrew on 2015-04-19.
 */

@Component
public class BusinessRepoImpl implements BusinessRepo {
    private static Logger logger = LoggerFactory.getLogger(BusinessRepoImpl.class.getCanonicalName());

    @Autowired
    BasicSearchClient searchClient;

    ExecutorService executorService = Executors.newFixedThreadPool(10);


    @Override
    public Collection<Adverts> search(Country country, Map<Parameter, String> options, String... keywords) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        Collection<Future<SearchResult>> futureResults = new ArrayList<>();
        HashSet<String> uniqueKeywords = new HashSet<>(Arrays.asList(keywords));
        for (String eachKeyword : uniqueKeywords) {
            futureResults.add(executorService.submit(new SearchRunner(country, options, eachKeyword)));
        }
        Collection<Adverts> toReturn = new ArrayList<>();
        for (Future<SearchResult> eachResult : futureResults) {
            SearchResult result = eachResult.get();
            if (result != null && result.getAdverts().size() > 0) toReturn.addAll(eachResult.get().getAdverts());
        }
        logger.debug("Search all " + keywords.length + " took " + (System.currentTimeMillis() - start) + "ms");
        return toReturn;
    }

    @Override
    public Adverts getById(Country country, String enrioId) throws AssertException, IOException, URISyntaxException {
        SearchResult result = searchClient.getById(country, enrioId, null);
        if (result != null && result.getAdverts().size() > 0) {
            return result.getAdverts().get(0);
        } else {
            return null;
        }
    }


    private class SearchRunner implements Callable<SearchResult> {
        private final Country country;
        private final Map<Parameter, String> options;
        private final String keyword;

        public SearchRunner(Country country, Map<Parameter, String> options, String keyword) {
            this.country = country;
            this.options = options;
            this.keyword = keyword;
        }

        @Override
        public SearchResult call() throws Exception {
            long start = System.currentTimeMillis();
            SearchResult toReturn = searchClient.search(keyword, country, options);
            logger.debug("Search keyword <" + keyword + "> took " + (System.currentTimeMillis() - start) + "ms");
            return toReturn;
        }
    }
}
