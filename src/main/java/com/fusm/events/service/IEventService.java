package com.fusm.events.service;

import com.fusm.events.model.EventModel;
import com.fusm.events.model.EventGeneralRequest;
import com.fusm.events.model.EventPersonalRequest;
import com.fusm.events.model.EventSearch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IEventService {

    List<EventModel> getEvents(EventSearch eventSearch);
    void createGeneralEvent(EventGeneralRequest eventRequest);
    void createPersonalEvent(EventGeneralRequest eventRequest);
    void updateGeneralEvent(EventGeneralRequest eventGeneralRequest, Integer eventId, Boolean isPersonal);
    void disableEvent(Integer eventId, Boolean isPersonal);

}
