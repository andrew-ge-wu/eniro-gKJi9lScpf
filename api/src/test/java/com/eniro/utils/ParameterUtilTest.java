package com.eniro.utils;

import com.eniro.api.constants.Parameter;
import com.eniro.api.util.ParameterMap;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 2015-04-19.
 */
public class ParameterUtilTest {
    @Test
    public void testGenerateParameterMap() throws Exception {
        Map<String, String> toTest = new HashMap<>();
        toTest.put(Parameter.geo_area.name(), "Stockholm");
        toTest.put("randomKey", "Stockholm");

        ParameterMap result = ParameterUtil.generateParameterMap(toTest);
        Assert.assertEquals("Stockholm", result.get(Parameter.geo_area));
        Assert.assertTrue(result.size() == 1);
    }
}