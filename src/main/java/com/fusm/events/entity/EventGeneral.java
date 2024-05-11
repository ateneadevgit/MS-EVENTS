package com.fusm.events.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Builder
@Entity
@Data
@Table(name = "event_general")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventGeneral {

    @Id
    @Column(name =  "id_event", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    @NonNull
    @Column(name = "tittle", length = 100, nullable = false)
    private String tittle;

    @NonNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "serial_repetition", length = 20, nullable = true)
    private String serialRepetition;

    @Column(name = "role_type", nullable = true)
    private Integer roleType;

    @NonNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "start_hour", nullable = true)
    private LocalTime startHour;

    @Column(name = "end_hour", nullable = true)
    private LocalTime endHour;

    @NonNull
    @Column(name = "repetition", nullable = false)
    private Integer repetition;

    @Column(name = "feedback", length = 255, nullable = true)
    private String feedback;

    @Column(name = "event_url", length = 200, nullable = false)
    private String eventUrl;

    @NonNull
    @Column(name = "event_type", nullable = false)
    private Integer eventType;

    @NonNull
    @Column(name = "all_day", nullable = false)
    private Boolean allDay;

    @NonNull
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @NonNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "faculty_id", nullable = true)
    private Integer facultyId;

    @Column(name = "program_id", nullable = true)
    private Integer programId;

    @Column(name = "role_id", nullable = true)
    private Integer roleId;

}
