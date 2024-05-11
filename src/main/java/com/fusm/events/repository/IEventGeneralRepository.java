package com.fusm.events.repository;

import com.fusm.events.dto.EventDto;
import com.fusm.events.entity.EventGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IEventGeneralRepository extends JpaRepository<EventGeneral, Integer> {

    @Query(value = "SELECT * FROM get_events_general(:startDate, :endDate, :userLevel, :facultyId, :programId)", nativeQuery = true)
    List<EventDto> findGeneralEvents(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("userLevel") Integer userLevel,
            @Param("facultyId") Integer facultyId,
            @Param("programId") Integer[] programId);

    List<EventGeneral> findAllBySerialRepetitionAndEnabled(String serial, Boolean enabled);

}
