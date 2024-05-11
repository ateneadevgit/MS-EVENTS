package com.fusm.events.external;

import com.fusm.events.model.CalendarEvent;
import com.fusm.events.model.EventSearchMoodle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMoodleService {

    CalendarEvent getMoodleEvents(EventSearchMoodle eventSearchMoodle);

}
