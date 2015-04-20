package com.eniro.api.util;

import com.eniro.api.constants.Parameter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 2015-04-18.
 */
public class ParameterMap extends HashMap<Parameter, String> {
    public ParameterMap(Map<? extends Parameter, ? extends String> m) {
        super(m);
    }

    public ParameterMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ParameterMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ParameterMap() {
        super();
    }

    public ParameterMap addParameter(Parameter parameter, Object object){
        put(parameter, object.toString());
        return this;
    }
}
