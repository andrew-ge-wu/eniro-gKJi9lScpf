package com.eniro.resource;

import com.eniro.TestConfiguration;
import com.eniro.api.constants.Country;
import com.eniro.model.SearchLogEntry;
import com.eniro.repository.SearchHistoryRepo;
import junit.framework.TestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by andrew on 2015-04-19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
public class SearchLogResourceTest {
    @Autowired
    SearchHistoryRepo logrepo;
    @Autowired
    BusinessResource businessResource;

    @Test
    public void testLogging() throws Exception {
        logrepo.clear();
        businessResource.searchBusinesses(Country.se.name(), new String[]{TestConfiguration.SW_1}, new HashMap<String, String>()).getBody();
        List<SearchLogEntry> result = logrepo.list(0, System.currentTimeMillis());
        Assert.assertTrue(result.size() == 1);
        Assert.assertArrayEquals(new String[]{TestConfiguration.SW_1},result.get(0).getKeywords());
    }

    @Test
    public void testDirectLog() throws Exception {
        String firstKey = RandomStringUtils.random(12);
        String secondKey = RandomStringUtils.random(12);
        logrepo.log(Country.no, null, firstKey);
        logrepo.log(Country.no, null, secondKey);
        Assert.assertEquals(secondKey, logrepo.list(0, System.currentTimeMillis()).get(0).getKeywords()[0]);
        logrepo.clear();
        Assert.assertTrue(logrepo.list(0, System.currentTimeMillis()).size() == 0);

    }
}