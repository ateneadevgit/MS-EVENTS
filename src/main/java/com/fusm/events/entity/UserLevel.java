package com.fusm.events.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Data
@Table(name = "user_level")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLevel {

    @Id
    @Column(name =  "id_user_level", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userLevelId;

    @NonNull
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @NonNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "description", nullable = true)
    private String description;

}
