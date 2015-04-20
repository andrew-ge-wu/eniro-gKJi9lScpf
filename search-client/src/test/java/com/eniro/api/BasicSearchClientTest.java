package com.eniro.api;

import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;
import com.eniro.api.exception.AssertException;
import com.eniro.api.model.SearchResult;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 2015-04-18.
 */
public class BasicSearchClientTest {

    @Test
    public void testSearch() throws Exception {
        BasicSearchClient client = new BasicSearchClient("andrew.ge.wu", "2741163023156419638");
        SearchResult result = client.search("pizza", Country.se, "stockholm");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getAdverts().size() == 25);
        result = client.search("pizza", Country.se, "stockholm", 1, 10);
        Assert.assertTrue(result.getAdverts().size() == 10);
    }

    @Test
    public void testPrepareRequest() throws Exception {
        BasicSearchClient client = new BasicSearchClient("andrew.ge.wu", "2741163023156419638");
        String searchString = RandomStringUtils.randomAlphabetic(12);
        String profile = RandomStringUtils.randomAlphabetic(12);
        String geoArea = RandomStringUtils.randomAlphabetic(12);
        Map<Parameter, String> overridingParams = new HashMap<Parameter, String>();
        overridingParams.put(Parameter.profile, profile);
        overridingParams.put(Parameter.geo_area, geoArea);
        overridingParams.put(Parameter.search_word, searchString);
        HttpGet result = client.prepareRequest(Country.no, overridingParams);
        Assert.assertTrue(result.getURI().toString().contains(Parameter.search_word.name() + "=" + searchString));
        Assert.assertTrue(result.getURI().toString().contains(Parameter.profile.name() + "=" + profile));
        Assert.assertTrue(result.getURI().toString().contains(Parameter.geo_area.name() + "=" + geoArea));
    }


    @Test(expected = AssertException.class)
    public void testErrorInit() throws Exception {
        new BasicSearchClient(null, "2741163023156419638");
    }


    @Test(expected = AssertException.class)
    public void testErrorNullParam() throws Exception {
        BasicSearchClient client = new BasicSearchClient("andrew.ge.wu", "2741163023156419638");
        client.search(null, Country.se, "stockholm", 1, 10);
    }

    @Test
    public void testGetById() throws Exception {
        BasicSearchClient client = new BasicSearchClient("andrew.ge.wu", "2741163023156419638");
        SearchResult result = client.getById(Country.se, "14957465");
        Assert.assertTrue(result.getAdverts().size()==1);
        Assert.assertEquals("14957465", result.getAdverts().get(0).getEniroId());
    }
}