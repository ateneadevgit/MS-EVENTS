package com.fusm.events.repository;

import com.fusm.events.entity.EventPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IEventPersonalRepository extends JpaRepository<EventPersonal, Integer> {

    List<EventPersonal> findAllBySerialRepetitionAndEnabled(String serial, Boolean enabled);

    @Query(
            value = "select * from event_personal " +
                    "where start_date between :startDate and :endDate " +
                    "and enabled = true " +
                    "and user_id like %:createdBy%",
            nativeQuery = true
    )
    List<EventPersonal> findPersonalEvents(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("createdBy") String createdBy);

}
