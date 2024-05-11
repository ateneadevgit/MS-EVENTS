package com.fusm.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSearchMoodle {

    private String createdBy;
    private Long startDate;
    private Long endDate;

}
