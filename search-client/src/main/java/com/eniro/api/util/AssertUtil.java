package com.eniro.api.util;

import com.eniro.api.exception.AssertException;

/**
 * Created by andrew on 2015-04-19.
 */
public class AssertUtil {
    public static void NotNull(Object toCheck, String name) throws AssertException {
        if (toCheck == null) throw new AssertException(name + " should not be null.");
    }
}
