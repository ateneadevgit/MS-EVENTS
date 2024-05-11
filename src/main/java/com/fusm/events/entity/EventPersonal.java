package com.fusm.events.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Builder
@Entity
@Data
@Table(name = "event_personal")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventPersonal {

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

    @NonNull
    @Column(name = "user_id", nullable = false)
    private String userId;

}
