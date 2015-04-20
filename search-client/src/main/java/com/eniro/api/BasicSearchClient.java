package com.eniro.api;

import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;
import com.eniro.api.exception.AssertException;
import com.eniro.api.http.HttpClientWrapper;
import com.eniro.api.model.SearchResult;
import com.eniro.api.util.AssertUtil;
import com.eniro.api.util.ParameterMap;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.util.Asserts;
import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 2015-04-18.
 */
public class BasicSearchClient {
    private static final String HOST = "http://api.eniro.com";
    private static final String VERSION = "1.1.3";
    private static final String BASE_PATH = "/cs/search/basic";
    private final HttpClientWrapper httpClient = new HttpClientWrapper();
    private final String profile;
    private final String key;


    public BasicSearchClient(String profile, String key) throws AssertException {
        AssertUtil.NotNull(profile, "profile");
        AssertUtil.NotNull(key, "key");
        this.profile = profile;
        this.key = key;
    }

    public SearchResult search(String searchWord, Country country, String area) throws IOException, URISyntaxException, AssertException {
        return search(searchWord, country, new ParameterMap().addParameter(Parameter.geo_area, area));
    }

    public SearchResult search(String searchWord, Country country, String area, int start, int end) throws IOException, URISyntaxException, AssertException {
        return search(searchWord, country, new ParameterMap().addParameter(Parameter.geo_area, area).addParameter(Parameter.from_list, start).addParameter(Parameter.to_list, end));
    }

    public SearchResult search(String searchWord, Country country, Map<Parameter, String> params) throws URISyntaxException, IOException, AssertException {
        AssertUtil.NotNull(country, "country");
        AssertUtil.NotNull(searchWord, "searchWord");
        if (params == null) params = new HashMap<Parameter, String>();
        params.put(Parameter.search_word, searchWord);
        return httpClient.request(prepareRequest(country, params), SearchResult.class);
    }

    public SearchResult getById(Country country, String eniorId) throws URISyntaxException, IOException, AssertException {
        return getById(country, eniorId, null);
    }

    public SearchResult getById(Country country, String eniorId, Map<Parameter, String> params) throws URISyntaxException, IOException, AssertException {
        AssertUtil.NotNull(country, "country");
        AssertUtil.NotNull(eniorId, "eniorId");
        if (params == null) params = new HashMap<Parameter, String>();
        params.put(Parameter.eniro_id, eniorId);
        return httpClient.request(prepareRequest(country, params), SearchResult.class);
    }


    protected HttpGet prepareRequest(Country country, Map<Parameter, String> params) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(HOST).setPath(BASE_PATH);
        builder.setParameter(Parameter.profile.name(), profile)
                .setParameter(Parameter.key.name(), key)
                .setParameter(Parameter.country.name(), country.name())
                .setParameter(Parameter.version.name(), VERSION);
        if (params != null && params.size() > 0) {
            for (Map.Entry<Parameter, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey().name(), entry.getValue());
            }
        }
        HttpGet toReturn = new HttpGet(builder.build());
        toReturn.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        toReturn.setHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
        return toReturn;
    }
}
