package com.eniro.utils;

import com.eniro.api.constants.Parameter;
import com.eniro.api.util.ParameterMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by andrew on 2015-04-19.
 */
public class ParameterUtil {
    private static final Logger logger = LoggerFactory.getLogger(ParameterUtil.class.getCanonicalName());

    public static ParameterMap generateParameterMap(Map<String, String> input) {
        ParameterMap toReturn = new ParameterMap(input.size());
        for (Map.Entry<String, String> eachInput : input.entrySet()) {
            try {
                toReturn.addParameter(Parameter.valueOf(eachInput.getKey()), eachInput.getValue());
            } catch (IllegalArgumentException e) {
                logger.info("Unknown parameter " + eachInput);
            }
        }
        return toReturn;
    }
}
