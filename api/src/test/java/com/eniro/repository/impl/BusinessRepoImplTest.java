package com.eniro.repository.impl;

import com.eniro.TestConfiguration;
import com.eniro.api.constants.Country;
import com.eniro.api.model.Adverts;
import com.eniro.api.util.ParameterMap;
import com.eniro.repository.BusinessRepo;
import junit.framework.TestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by andrew on 2015-04-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
public class BusinessRepoImplTest {
    @Autowired
    BusinessRepo toTest;


    @Test
    public void testSearchSingle() throws Throwable {
        Collection<Adverts> result = toTest.search(Country.se, new ParameterMap(), TestConfiguration.SW_1);
        Assert.assertTrue(result.size()==1);
        Assert.assertEquals(TestConfiguration.ORG_1, result.iterator().next().getEniroId());
    }
    @Test
    public void testSearchMulti() throws Throwable {
        Collection<Adverts> result = toTest.search(Country.se, new ParameterMap(), TestConfiguration.SW_1,TestConfiguration.SW_2);
        Assert.assertTrue(result.size() == 2);
        Set<String> seachWords=new HashSet<>();
        seachWords.add(TestConfiguration.ORG_1);
        seachWords.add(TestConfiguration.ORG_2);

        Iterator<Adverts> iterator = result.iterator();
        Assert.assertTrue(seachWords.contains(iterator.next().getEniroId()));
        Assert.assertTrue(seachWords.contains(iterator.next().getEniroId()));
    }

    @Test
    public void testGetByIdFail() throws Throwable {
        Assert.assertEquals(null, toTest.getById(Country.se, RandomStringUtils.randomAlphabetic(10)));
    }

    @Test
    public void testGetByIdSuccess() throws Throwable {
        Assert.assertEquals(TestConfiguration.ORG_1, toTest.getById(Country.se, TestConfiguration.ORG_1).getEniroId());
    }
}