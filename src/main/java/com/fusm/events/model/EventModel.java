package com.fusm.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventModel {

    private String tittle;
    private Date start;
    private Date end;
    private Integer type;
    private Boolean allDay;
    private EventMeta meta;

}
