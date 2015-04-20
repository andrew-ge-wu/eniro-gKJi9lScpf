package com.eniro.resource;

import com.eniro.api.constants.Country;
import com.eniro.api.model.Adverts;
import com.eniro.api.util.ParameterMap;
import com.eniro.repository.BusinessRepo;
import com.eniro.repository.SearchHistoryRepo;
import com.eniro.utils.ParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import static com.eniro.Constants.*;

/**
 * Created by andrew on 2015-04-18.
 */

@RestController
public class BusinessResource {

    @Autowired
    BusinessRepo businessRepo;

    @Autowired
    SearchHistoryRepo searchHistoryRepo;


    @RequestMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Country[]> getCountries() {
        return new ResponseEntity<>(Country.values(), HttpStatus.OK);
    }

    @RequestMapping(value = "/countries/{" + PATH_PARAM_COUNTRY + "}/businesses/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Collection<Adverts>> searchBusinesses(@PathVariable(PATH_PARAM_COUNTRY) String country,
                                                                @RequestParam(value = QUERY_PARAM_SEARCH_WORD, required = true) String[] keywords,
                                                                @RequestParam Map<String, String> allParams) {
        try {
            allParams.remove(QUERY_PARAM_SEARCH_WORD);
            ParameterMap eniroParamap = ParameterUtil.generateParameterMap(allParams);
            searchHistoryRepo.log(Country.valueOf(country), eniroParamap, keywords);
            return new ResponseEntity<>(businessRepo.search(Country.valueOf(country), eniroParamap, keywords), HttpStatus.OK);
        } catch (IllegalArgumentException | URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Throwable throwable) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/countries/{" + PATH_PARAM_COUNTRY + "}/businesses/{" + QUERY_PARAM_ENIRO_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Adverts> getBusiness(@PathVariable(PATH_PARAM_COUNTRY) String country,
                                               @PathVariable(QUERY_PARAM_ENIRO_ID) String eniroId) {
        try {
            return new ResponseEntity<>(businessRepo.getById(Country.valueOf(country), eniroId), HttpStatus.OK);
        } catch (IllegalArgumentException | URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Throwable throwable) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
