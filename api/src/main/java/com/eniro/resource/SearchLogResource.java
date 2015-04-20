package com.eniro.resource;

import com.eniro.Constants;
import com.eniro.api.constants.Country;
import com.eniro.api.model.Adverts;
import com.eniro.model.SearchLogEntry;
import com.eniro.repository.BusinessRepo;
import com.eniro.repository.SearchHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 2015-04-18.
 */

@RestController
public class SearchLogResource {

    @Autowired
    SearchHistoryRepo historyRepo;

    @RequestMapping(value = "/logs", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<SearchLogEntry>> getLogs(@RequestParam(value = Constants.QUERY_PARAM_LOG_START, required = true) String fromTime,
                                                        @RequestParam(value = Constants.QUERY_PARAM_LOG_END, required = false) String toTime) {

        long fromTimestamp = Long.valueOf(fromTime);
        long toTimestamp;
        if (toTime != null) {
            toTimestamp = Long.valueOf(toTime);
        } else {
            toTimestamp = System.currentTimeMillis();
        }
        return new ResponseEntity<>(historyRepo.list(fromTimestamp, toTimestamp), HttpStatus.OK);
    }

    @RequestMapping(value = "/logs", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<List<SearchLogEntry>> deleteLogs() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
