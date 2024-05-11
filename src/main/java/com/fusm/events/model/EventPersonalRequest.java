package com.fusm.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPersonalRequest {

    private String tittle;
    private Date startDate;
    private Date endDate;
    private LocalTime startHour;
    private LocalTime endHour;
    private Integer repetition;
    private String feedback;
    private String eventUrl;
    private Boolean isAllDay;
    private String createdBy;
    private String userId;

}
