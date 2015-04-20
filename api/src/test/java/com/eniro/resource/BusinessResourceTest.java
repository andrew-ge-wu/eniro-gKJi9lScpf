package com.eniro.resource;

import com.eniro.ResourceUtil;
import com.eniro.TestConfiguration;
import com.eniro.api.BasicSearchClient;
import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;
import com.eniro.api.model.Adverts;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 2015-04-19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
public class BusinessResourceTest {

    @Autowired
    BusinessResource businessResource;

    @Autowired
    BasicSearchClient client;

    @Test
    public void testGetCountries() throws Exception {
        Assert.assertArrayEquals(Country.values(), businessResource.getCountries().getBody());
    }

    @Test
    public void testSearchBusinesses() throws Exception {
        Collection<Adverts> result = businessResource.searchBusinesses(Country.se.name(), new String[]{TestConfiguration.SW_1}, new HashMap<String, String>()).getBody();
        Assert.assertTrue(result.size() == 1);
        Assert.assertEquals(TestConfiguration.ORG_1, result.iterator().next().getEniroId());
    }

    @Test
    public void testGetBusiness() throws Exception {
        Adverts result = businessResource.getBusiness(Country.se.name(), TestConfiguration.ORG_1).getBody();
        Assert.assertEquals(TestConfiguration.ORG_1, result.getEniroId());

    }
}