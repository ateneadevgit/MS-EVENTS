package com.fusm.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMeta {

    private String feedback;
    private String eventUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startHour;
    private LocalTime endHour;
    private Integer eventType;
    private Integer facultyId;
    private Integer programId;
    private Integer roleId;
    private Boolean isAllDay;
    private Boolean canEdit;
    private Boolean isPersonal;
    private String createdBy;
    private Integer roleType;
    private Integer eventId;
    private Integer repetition;

}
