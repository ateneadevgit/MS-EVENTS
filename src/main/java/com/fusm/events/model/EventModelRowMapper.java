package com.fusm.events.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EventModelRowMapper implements RowMapper<EventModel> {

    @Override
    public EventModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return EventModel.builder()
                .tittle(rs.getString("tittle"))
                .start(Timestamp.valueOf(LocalDateTime.of(rs.getDate("start_date").toLocalDate(), rs.getTime("start_hour").toLocalTime())))
                .end(Timestamp.valueOf(LocalDateTime.of(rs.getDate("end_date").toLocalDate(), rs.getTime("end_hour").toLocalTime())))
                .type(rs.getInt("event_type"))
                .allDay(rs.getBoolean("all_day"))
                .meta(
                        EventMeta.builder()
                                .feedback(rs.getString("feedback"))
                                .eventUrl(rs.getString("event_url"))
                                .startDate(rs.getDate("start_date").toLocalDate())
                                .startHour(rs.getTime("start_hour").toLocalTime())
                                .endDate(rs.getDate("end_date").toLocalDate())
                                .endHour(rs.getTime("end_hour").toLocalTime())
                                .eventType(rs.getInt("event_type"))
                                .facultyId(rs.getInt("faculty_id"))
                                .programId(rs.getInt("program_id"))
                                .roleId(rs.getInt("role_id"))
                                .isAllDay(rs.getBoolean("all_day"))
                                .canEdit(false)
                                .isPersonal(false)
                                .createdBy(rs.getString("created_by"))
                                .roleType(rs.getInt("role_type"))
                                .eventId(rs.getInt("id_event"))
                                .repetition(rs.getInt("repetition"))
                                .build()
                )
                .build();
    }

}
