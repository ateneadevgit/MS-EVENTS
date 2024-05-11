package com.fusm.events.dto;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalTime;

public interface EventDto {

    @Value("#{target.id_event}")
    Integer getEventId();

    @Value("#{target.tittle}")
    String getTittle();

    @Value("#{target.start_date}")
    LocalDate getStartDate();

    @Value("#{target.start_hour}")
    LocalTime getStartHour();

    @Value("#{target.end_date}")
    LocalDate getEndDate();

    @Value("#{target.end_hour}")
    LocalTime getEndHour();

    @Value("#{target.event_type}")
    Integer getEventType();

    @Value("#{target.all_day}")
    Boolean getAllDay();

    @Value("#{target.feedback}")
    String getFeedback();

    @Value("#{target.event_url}")
    String getEventUrl();

    @Value("#{target.created_by}")
    String getCreatedBy();

    @Value("#{target.faculty_id}")
    Integer getFacultyId();

    @Value("#{target.program_id}")
    Integer getProgramId();

    @Value("#{target.role_id}")
    Integer getRoleId();

}
