package com.fusm.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEvent {

    private List<EventModelMoodle> events;
    private List<String> warnings;

}
