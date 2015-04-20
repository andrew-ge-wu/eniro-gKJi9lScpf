package com.eniro.repository.impl;

import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;
import com.eniro.api.util.ParameterMap;
import com.eniro.model.SearchLogEntry;
import com.eniro.repository.SearchHistoryRepo;
import junit.framework.TestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 2015-04-19.
 */
public class SearchHistoryRepoImplTest {

    @Test
    public void testLog() throws Exception {
        SearchHistoryRepo repo = new SearchHistoryRepoImpl();
        String searchWord = RandomStringUtils.randomAlphabetic(12);
        ParameterMap parameterMap = new ParameterMap()
                .addParameter(Parameter.search_word, searchWord);
        repo.log(Country.se, parameterMap, searchWord, searchWord);
        repo.log(Country.se, parameterMap, searchWord, searchWord);
        List<SearchLogEntry> result = repo.list(0, System.currentTimeMillis());
        Assert.assertTrue(result.size() == 2);
        Assert.assertTrue(result.get(0).getKeywords().length == 2);
        Assert.assertTrue(result.get(0).getOptions().size() == 1);
        Assert.assertEquals(searchWord, result.get(0).getKeywords()[0]);
        Assert.assertEquals(searchWord, result.get(0).getOptions().get(Parameter.search_word));
    }

    @Test
    public void testSelectRange() throws Exception {

        SearchHistoryRepoImpl repo = new SearchHistoryRepoImpl();

        List<SearchLogEntry> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(new SearchLogEntry(i * 1000, null, null, null, null));
        }
        List<SearchLogEntry> result = repo.selectRange(list, 2000, 5000);
        Assert.assertTrue(result.size() == 4);
        Assert.assertTrue(result.get(0).getSearchDate() == 5000);
        Assert.assertTrue(result.get(3).getSearchDate() == 2000);
        Assert.assertTrue(repo.selectRange(list, 12000, 15000).isEmpty());
        Assert.assertTrue(repo.selectRange(list, 10, 99).isEmpty());
        result = repo.selectRange(list, 2000, 12000);
        Assert.assertTrue(result.size() == 9);
        Assert.assertTrue(result.get(0).getSearchDate() == 10000);
        Assert.assertTrue(result.get(8).getSearchDate() == 2000);


        result = repo.selectRange(list, 10, 8000);
        Assert.assertTrue(result.size() == 8);
        Assert.assertTrue(result.get(0).getSearchDate() == 8000);
        Assert.assertTrue(result.get(7).getSearchDate() == 1000);

    }

    @Test
    public void testSelectRangePartitioned() throws Exception {

        SearchHistoryRepoImpl repo = new SearchHistoryRepoImpl();


        for (int i = 1; i <= 10; i++) {
            repo.log(new SearchLogEntry(i * 10000, null, null, null, null));
        }
        List<SearchLogEntry> result = repo.list(20000, 50000);
        Assert.assertTrue(result.size() == 4);
        Assert.assertTrue(result.get(0).getSearchDate() == 50000);
        Assert.assertTrue(result.get(3).getSearchDate() == 20000);
        Assert.assertTrue(repo.list(120000, 150000).isEmpty());
        Assert.assertTrue(repo.list(10, 99).isEmpty());
        result = repo.list(20000, 120000);
        Assert.assertTrue(result.size() == 9);
        Assert.assertTrue(result.get(0).getSearchDate() == 100000);
        Assert.assertTrue(result.get(8).getSearchDate() == 20000);


        result = repo.list( 10, 80000);
        Assert.assertTrue(result.size() == 8);
        Assert.assertTrue(result.get(0).getSearchDate() == 80000);
        Assert.assertTrue(result.get(7).getSearchDate() == 10000);

    }
}