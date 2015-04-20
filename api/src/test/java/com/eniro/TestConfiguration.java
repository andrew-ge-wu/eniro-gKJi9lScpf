package com.eniro;

import com.eniro.api.BasicSearchClient;
import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;
import com.eniro.api.exception.AssertException;
import com.eniro.api.model.SearchResult;
import com.eniro.api.util.ParameterMap;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by andrew on 2015-04-19.
 */
@Configuration
@ComponentScan("com.eniro")
public class TestConfiguration {
    public static final String ORG_1 = "14957465";
    public static final String ORG_2 = "14976381";
    public static final String SW_1 = "sw1";
    public static final String SW_2 = "sw2";


    @Bean
    public BasicSearchClient basicSearchClient() throws IOException, URISyntaxException, AssertException {
        BasicSearchClient toReturn = Mockito.mock(BasicSearchClient.class);
        Mockito.when(toReturn.getById(Country.se, ORG_1, null)).thenReturn(ResourceUtil.loadResource("/org_" + ORG_1 + ".json", SearchResult.class));
        Mockito.when(toReturn.getById(Country.se, ORG_2, null)).thenReturn(ResourceUtil.loadResource("/org_" + ORG_2 + ".json", SearchResult.class));
        Mockito.when(toReturn.search(SW_1, Country.se, new ParameterMap())).thenReturn(ResourceUtil.loadResource("/org_" + ORG_1 + ".json", SearchResult.class));
        Mockito.when(toReturn.search(SW_2, Country.se, new ParameterMap())).thenReturn(ResourceUtil.loadResource("/org_" + ORG_2 + ".json", SearchResult.class));
        return toReturn;
    }
}
