package com.eniro.repository;

import com.eniro.api.constants.Country;
import com.eniro.api.constants.Parameter;
import com.eniro.api.exception.AssertException;
import com.eniro.api.model.Adverts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by andrew on 2015-04-19.
 */
public interface BusinessRepo {
    Collection<Adverts> search(Country country, Map<Parameter, String> options, String... keywords) throws Throwable;

    Adverts getById(Country country, String enrioId) throws Throwable;
}
