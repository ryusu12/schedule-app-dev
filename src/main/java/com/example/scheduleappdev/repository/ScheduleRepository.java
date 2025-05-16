package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}