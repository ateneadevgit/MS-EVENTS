package com.fusm.events.repository;

import com.fusm.events.model.EventModel;
import com.fusm.events.model.EventModelRowMapper;
import com.fusm.events.model.EventSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EventGeneralCustomRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<EventModel> callDynamicSelect(EventSearch eventSearch, Integer userLevel) {
        String sql = "SELECT * FROM get_events_general(?, ?, ?, ?, ?)";
        return jdbcTemplate.query(sql, new Object[] {
                        eventSearch.getStartDate(),
                        eventSearch.getEndDate(),
                        userLevel,
                        eventSearch.getFacultyId(),
                        eventSearch.getProgramId()
                },
                new EventModelRowMapper()
        );
    }
}
