package com.fusm.events.external.impl;

import com.fusm.events.external.IMoodleService;
import com.fusm.events.model.CalendarEvent;
import com.fusm.events.model.EventSearchMoodle;
import com.fusm.events.webclient.WebClientConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MoodleService implements IMoodleService {

    @Autowired
    private WebClientConnector webClientConnector;

    @Value("${ms-moodle.complete-path}")
    private String MOODLE_ROUTE;

    @Value("${ms-moodle.path}")
    private String MOODLE_SERVICE;


    @Override
    public CalendarEvent getMoodleEvents(EventSearchMoodle eventSearchMoodle) {
        return webClientConnector.connectWebClient(MOODLE_ROUTE)
                .post()
                .uri(MOODLE_SERVICE + "/user")
                .bodyValue(eventSearchMoodle)
                .retrieve()
                .bodyToMono(CalendarEvent.class)
                .block();
    }

}
