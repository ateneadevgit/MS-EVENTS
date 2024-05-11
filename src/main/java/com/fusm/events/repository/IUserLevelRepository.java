package com.fusm.events.repository;

import com.fusm.events.entity.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserLevelRepository extends JpaRepository<UserLevel, Integer> {

    Optional<UserLevel> findByRoleId(Integer roleId);

}
